package app.s2c.data.builder

import app.s2c.data.extensions.camelCase
import app.s2c.data.extensions.indented
import app.s2c.data.extensions.pascalCase
import app.s2c.data.model.IconFileContents
import app.s2c.data.model.ImageVectorNode

class MaterialIconSourceBuilder : IconSourceBuilder() {

    override val imports: Set<String> = setOf(
        "androidx.compose.material.Icon",
        "androidx.compose.material.icons.materialIcon",
        "androidx.compose.material.icons.materialPath",
    )

    override fun IconSourceBuilderScope.stringify(icon: IconFileContents): String = with(icon) {
        """
            |${pkg?.let { "package $it" } ?: ""}
            |
            |${this@stringify.imports.sorted().joinToString("\n") { "import $it" }}
            |
            |${visibilityModifier}val $iconPropertyName: ImageVector
            |    get() {
            |        if (_${iconName.camelCase()} != null) return _${iconName.camelCase()}!!
            |
            |        _${iconName.camelCase()} = materialIcon(name = "$receiverType.${iconName.pascalCase()}") {
            |            $pathNodes
            |        }
            |        return _${iconName.camelCase()}!!
            |    }
            |@Suppress("ObjectPropertyName")
            |private var _${iconName.camelCase()}: ImageVector? = null
            |$EXTRA_CONTENT_PLACEHOLDER
            |
        """.replace(EXTRA_CONTENT_PLACEHOLDER, extraContent).trimMargin()
    }

    override fun IconSourceBuilderScope.stringifyPath(path: ImageVectorNode.Path): String = with(path) {
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
                |${comment}materialPath$pathParamsString {
                |    $pathNodes
                |}
            """.trimMargin()
    }

    override fun ImageVectorNode.Path.buildParameterList(): List<Pair<String, String>> = buildList {
        with(params) {
            fillAlpha?.let { add("fillAlpha" to "${it}f") }
            strokeAlpha?.let { add("strokeAlpha" to "${it}f") }
            pathFillType?.let { add("pathFillType" to "${it.toCompose()}") }
        }
    }
}