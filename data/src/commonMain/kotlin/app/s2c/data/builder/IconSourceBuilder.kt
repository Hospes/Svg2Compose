package app.s2c.data.builder

import app.s2c.data.extensions.camelCase
import app.s2c.data.extensions.indented
import app.s2c.data.extensions.pascalCase
import app.s2c.data.logger.verbose
import app.s2c.data.logger.warn
import app.s2c.data.model.IconFileContents
import app.s2c.data.model.ImageVectorNode
import app.s2c.data.model.ImageVectorNode.Group.Companion.CLIP_PATH_PARAM_NAME
import app.s2c.data.model.svg.SvgColor
import app.s2c.data.model.svg.toBrush
import app.s2c.data.parser.method.MethodSizeAccountable
import kotlin.math.ceil
import kotlin.math.roundToInt

abstract class IconSourceBuilder {

    open val imports: Set<String> = emptySet()

    /**
     * Generates the Kotlin code for the icon.
     *
     * @return The generated Kotlin code.
     */
    fun materialize(icon: IconFileContents): String = with(icon) {
        val (nodes, chunkFunctions) = chunkNodesIfNeeded()

        val scope = object : IconSourceBuilderScope {
            override val imports: Set<String> = this@IconSourceBuilder.imports + icon.imports
            override val iconPropertyName: String = buildIconPropertyName(receiverType)

            override val pathNodes: String
                get() {
                    val indentSize = 12
                    return nodes
                        .joinToString("\n${" ".repeat(indentSize)}") {
                            stringifyNode(it)
                                .replace("\n", "\n${" ".repeat(indentSize)}") // fix indent
                        }
                }

            override val extraContent: String
                get() {
                    val chunkFunctionsContent = buildChunkFunctionsContent(chunkFunctions)
                    val preview = buildPreview(iconPropertyName)
                    return buildExtraContent(chunkFunctionsContent, preview)
                }

            override val visibilityModifier: String = if (makeInternal) "internal " else ""
        }

        verbose(
            """Parameters:
           |    package=$pkg
           |    icon_name=$iconName
           |    width=$width
           |    height=$height
           |    viewport_width=$viewportWidth
           |    viewport_height=$viewportHeight
           |    nodes=${nodes.map { scope.stringifyNode(it) }}
           |    receiver_type=$receiverType
           |    imports=$imports
           |
            """.trimMargin()
        )
        return@with scope.stringify(icon)
    }


    internal abstract fun IconSourceBuilderScope.stringify(icon: IconFileContents): String

    private fun IconSourceBuilderScope.stringifyNode(node: ImageVectorNode): String = when (node) {
        is ImageVectorNode.Path -> stringifyPath(node)
        is ImageVectorNode.Group -> stringifyGroup(node)
        is ImageVectorNode.ChunkFunction -> stringifyChunkFunction(node)
    }

    internal open fun IconSourceBuilderScope.stringifyPath(path: ImageVectorNode.Path): String = with(path) {
        val indentSize = 4
        val pathNodes = wrapper.nodes.joinToString("\n${" ".repeat(indentSize)}") {
            it.materialize()
                .replace("\n", "\n${" ".repeat(indentSize)}") // Fix indent
                .trimEnd()
        }

        val pathParams = buildParameterList()

        val pathParamsString = if (pathParams.isNotEmpty()) {
            """(
                |${pathParams.joinToString("\n") { (param, value) -> "$param = $value,".indented(4) }}
                |)"""
        } else {
            ""
        }

        val comment = if (minified) "" else "// ${wrapper.normalizedPath}\n|"

        return """
                |${comment}path$pathParamsString {
                |    $pathNodes
                |}
            """.trimMargin()
    }

    internal open fun ImageVectorNode.Path.buildParameterList(): List<Pair<String, String>> = buildList {
        with(params) {
            params.fill?.let { brush -> brush.toCompose()?.let { add("fill" to it) } }
            fillAlpha?.let { add("fillAlpha" to "${it}f") }
            pathFillType?.let { add("pathFillType" to "${it.toCompose()}") }
            stroke?.let { stroke -> stroke.toCompose()?.let { add("stroke" to it) } }
            strokeAlpha?.let { add("strokeAlpha" to "${it}f") }
            strokeLineCap?.let { add("strokeLineCap" to "${it.toCompose()}") }
            strokeLineJoin?.let { add("strokeLineJoin" to "${it.toCompose()}") }
            strokeMiterLimit?.let { add("strokeLineMiter" to "${it}f") }
            strokeLineWidth?.let { add("strokeLineWidth" to "${it}f") }

            if (params.fill == null && params.stroke == null) {
                add("fill" to requireNotNull(SvgColor.Default.toBrush().toCompose()))
            }
        }
    }

    internal open fun IconSourceBuilderScope.stringifyGroup(group: ImageVectorNode.Group): String = with(group) {
        val indentSize = 4
        val groupPaths = commands
            .joinToString("\n${" ".repeat(indentSize)}") {
                stringifyNode(it)
                    .replace("\n", "\n${" ".repeat(indentSize)}")
                    .trimEnd()
            }

        val groupParams = buildParameters(indentSize)

        val groupParamsString = if (groupParams.isNotEmpty()) {
            val params = groupParams.joinToString("\n") { (param, value) ->
                if (param == CLIP_PATH_PARAM_NAME && minified.not() && params.clipPath != null) {
                    "${"// ${params.clipPath.normalizedPath}".indented(4)}\n"
                } else {
                    ""
                } + "$param = $value,".indented(indentSize)
            }
            """(
                |$params
                |)"""
        } else {
            ""
        }

        return """
                |group$groupParamsString {
                |    $groupPaths
                |}
            """.trimMargin()
    }

    internal open fun IconSourceBuilderScope.stringifyChunkFunction(func: ImageVectorNode.ChunkFunction): String = with(func) { "$functionName()" }


    /**
     * Builds the content of the `chunkFunctions` property for the generated class.
     *
     * @param chunkFunctions The list of `ChunkFunction` objects.
     * @return The content of the `chunkFunctions` property as a string.
     */
    private fun IconSourceBuilderScope.buildChunkFunctionsContent(chunkFunctions: List<ImageVectorNode.ChunkFunction>?) =
        if (!chunkFunctions.isNullOrEmpty()) {
            """|
               |${chunkFunctions.joinToString("\n\n") { createChunkFunction(it) }}
               """
        } else {
            ""
        }

    /**
     * Create the chunk function wrapping the `path`/`group` instructions
     * within a smaller method.
     */
    private fun IconSourceBuilderScope.createChunkFunction(func: ImageVectorNode.ChunkFunction): String = with(func) {
        val indentSize = 4
        val bodyFunction = nodes.joinToString("\n${" ".repeat(indentSize)}") {
            stringifyNode(it)
                .replace("\n", "\n${" ".repeat(indentSize)}") // fix indent
        }

        return """
                    |private fun ImageVector.Builder.$functionName() {
                    |    $bodyFunction
                    |}
            """.trimMargin()
    }

    private fun IconFileContents.buildIconPropertyName(receiverType: String?) = when {
        receiverType?.isNotEmpty() == true -> {
            // as we add the dot in the next line, remove it in case the user adds a leftover dot
            // to avoid compile issues.
            receiverType.removeSuffix(".")
            "$receiverType.${iconName.pascalCase()}"
        }

        addToMaterial -> "Icons.Filled.${iconName.pascalCase()}"
        else -> iconName.pascalCase()
    }

    /**
     * Builds the preview code for the given icon property name.
     *
     * @param iconPropertyName The name of the icon property to generate the preview for.
     * @return The preview code as a string.
     */
    private fun IconFileContents.buildPreview(iconPropertyName: String) = if (noPreview) {
        ""
    } else {
        """
        |
        |@Preview
        |@Composable
        |private fun Preview() = Icon(imageVector = $iconPropertyName, contentDescription = null)
        """
    }

    /**
     * Builds a string containing the extra content, which includes the chunk
     * functions and the preview.
     *
     * @param chunkFunctionsContent The string representation of the chunk functions.
     * @param preview The preview string.
     * @return A string containing the extra content.
     */
    private fun buildExtraContent(chunkFunctionsContent: String, preview: String) = buildString {
        if (chunkFunctionsContent.isNotEmpty()) {
            appendLine(chunkFunctionsContent.trimMargin())
        }
        if (preview.isNotEmpty()) {
            appendLine(preview.trimMargin())
        }
    }

    /**
     * Checks if the size of the icon exceeds the threshold for a single method
     * and splits the icon into chunks if needed.
     *
     * @return A pair containing the list of nodes and the list of chunk functions
     * (if any).
     */
    private fun IconFileContents.chunkNodesIfNeeded(): Pair<List<ImageVectorNode>, List<ImageVectorNode.ChunkFunction>?> {
        val byteSize = ICON_BASE_STRUCTURE_BYTE_SIZE + nodes
            .sumOf { it.approximateByteSize }
        val shouldChunkNodes = byteSize > MethodSizeAccountable.METHOD_SIZE_THRESHOLD

        val nodes = if (shouldChunkNodes) {
            var i = 1
            val chunks = ceil(byteSize.toFloat() / MethodSizeAccountable.METHOD_SIZE_THRESHOLD)
                .roundToInt()
            val chunkSize = nodes.size / chunks
            warn(
                "Potential large icon detected. Splitting icon's content in $chunks chunks to avoid " +
                        "compilation issues. However, that won't affect the performance of displaying this icon."
            )
            nodes.chunked(chunkSize) { chunk ->
                val snapshot = chunk.toList()
                ImageVectorNode.ChunkFunction(
                    functionName = "${iconName.camelCase()}Chunk${i++}",
                    nodes = snapshot,
                )
            }
        } else {
            nodes
        }
        val chunkFunctions = if (shouldChunkNodes) {
            nodes.filterIsInstance<ImageVectorNode.ChunkFunction>()
        } else {
            null
        }

        return nodes to chunkFunctions
    }

    companion object {
        private const val ICON_BASE_STRUCTURE_BYTE_SIZE = 73
        const val EXTRA_CONTENT_PLACEHOLDER = "[EXTRA_CONTENT_PLACEHOLDER]"
    }
}

internal interface IconSourceBuilderScope {
    val imports: Set<String>
    val iconPropertyName: String
    val pathNodes: String
    val extraContent: String
    val visibilityModifier: String
}