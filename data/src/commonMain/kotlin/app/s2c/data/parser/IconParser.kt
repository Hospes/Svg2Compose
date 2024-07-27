package app.s2c.data.parser

import app.s2c.data.domain.*
import app.s2c.data.domain.avg.AvgRootNode
import app.s2c.data.domain.avg.asNodes
import app.s2c.data.domain.svg.SvgRootNode
import app.s2c.data.domain.svg.asNodes
import okio.FileSystem
import okio.Path

/**
 * A sealed class [IconParser] that provides methods to parse SVG
 * and Android Vector Drawable Images and returns an [IconFileContents]
 * object.
 *
 * The class requires a [FileSystem] parameter.
 *
 * @property fileSystem a [FileSystem] instance that allows reading from the file system.
 * @constructor Creates an [IconParser] object with the specified [FileSystem].
 *
 * @see [IconParser.SvgParser]
 * @see [IconParser.AndroidVectorParser]
 */
sealed interface IconParser {

    /**
     * Parse an SVG/AVG icon and creates a [IconFileContents] object containing
     * all the required information to generate the Jetpack Compose Icon.
     *
     * @param file This is a [Path] object that points towards the file that
     * needs to be parsed into an icon.
     * @param iconName This is a String parameter that serves as the identifier
     * for the icon that will be generated after parsing the file.
     * @param config An instance of the [ParserConfig] class, which contains
     * configurations required for parsing the file into an icon.
     * @return An [IconFileContents] object instance which contains information
     * about the icon parsed from the file.
     */
    fun parse(
        content: String,
        iconName: String,
        config: ParserConfig,
    ): IconFileContents

    /**
     * This function generates the Icon's imports for the provided list of
     * [ImageVectorNode] and configuration.
     *
     * It first adds all the default imports to the returned set.
     *
     * Then, based on the provided configuration and [ImageVectorNode] list,
     * other imports might also be added:
     * - If the [ParserConfig.noPreview] property of the [config] parameter
     * is `false`, the preview imports will be added too.
     * - If the [ImageVectorNode] list contains an [ImageVectorNode.Group],
     * the group imports are added.
     * - If the [ParserConfig.addToMaterial] property of the [config] object
     * is `true`, the material context provider import is included.
     * - For each [ImageVectorNode.Path] found in the [nodes] list, the
     * associated path imports are added.
     *
     * @param nodes a List of [ImageVectorNode]s to be processed for possible
     * additional imports.
     * @param config the parser configuration based on which additional
     * decisions for imports are taken.
     * @return a set of [String]s representing all the required imports.
     *
     * @see defaultImports
     * @see previewImports
     * @see materialReceiverTypeImport
     * @see ImageVectorNode.Path.imports
     * @see ImageVectorNode.Group.imports
     */
    fun createImports(
        nodes: List<ImageVectorNode>,
        config: ParserConfig,
    ): Set<String> = buildSet {
        addAll(defaultImports)
        if (config.noPreview.not()) {
            addAll(previewImports)
        }
        if (config.addToMaterial) {
            addAll(materialReceiverTypeImport)
        }
        val nodeImports = nodes
            .asSequence()
            .flatMap {
                if (it is ImageVectorNode.ChunkFunction) {
                    it.nodes
                } else {
                    listOf(it)
                }
            }
            .flatMap { node ->
                if (node is ImageVectorNode.Group) {
                    node.imports + node.commands.flatMap { node.imports }
                } else {
                    node.imports
                }
            } // consider group
            .toSet()

        addAll(nodeImports)
    }


    /**
     * [SvgParser] is a subclass of [IconParser].
     *
     * This class is responsible for parsing an SVG file type and creates
     * all the required information to generate a Jetpack Compose Icon.
     *
     * @constructor Takes a FileSystem parameter.
     *
     * @param fileSystem The Main tool that helps to manage files and allows
     *  reading data from the file system.
     */
    data object SvgParser : IconParser {
        /**
         * Parses an SVG file into an [IconFileContents] object.
         *
         * The parsing procedure can be summed up as follows:
         * 1. Read the content of SVG file.
         * 2. Parses the content of file to a [SvgRootNode] object.
         * 3. Converts SVG element nodes to [ImageVectorNode] to get the list
         * of nodes.
         * 4. Lastly, an [IconFileContents] instance is assembled with the necessary
         * data needed to create the Jetpack Compose Icon and returns it.
         *
         * @param file This is a [Path] object that points to the file that needs
         * to be parsed into an icon.
         * @param iconName A string parameter that serves as the identifier for the
         * icon that will be generated after parsing the file.
         * @param config An instance of the [ParserConfig] class, which holds
         * configurations required for parsing the file into an icon.
         *
         * @return [IconFileContents] object instance which contains details about the
         * icon parsed from the file.
         */
        override fun parse(
            content: String,
            iconName: String,
            config: ParserConfig,
        ): IconFileContents {
            val root = XmlParser.parse(content = content, fileType = FileType.Svg)
            val svg = root.children.single { it is SvgRootNode } as SvgRootNode
            svg.resolveUseNodes()
            val (_, _, viewportWidth, viewportHeight) = svg.viewBox
            val nodes = svg
                .asNodes(minified = config.minified)
                .map {
                    it.applyTransformation()
                }

            return IconFileContents(
                pkg = config.pkg,
                iconName = iconName,
                theme = config.theme,
                width = svg.width,
                height = svg.height,
                viewportWidth = viewportWidth,
                viewportHeight = viewportHeight,
                nodes = nodes,
                receiverType = config.receiverType,
                addToMaterial = config.addToMaterial,
                noPreview = config.noPreview,
                makeInternal = config.makeInternal,
                imports = createImports(nodes, config),
            )
        }
    }

    /**
     * [AndroidVectorParser] is a subclass of [IconParser].
     *
     * This class is responsible for parsing an AVG file type and creates
     * all the required information to generate a Jetpack Compose Icon.
     *
     * @constructor Takes a FileSystem parameter.
     *
     * @param fileSystem The Main tool that helps to manage files and allows
     * reading data from the file system.
     */
    data object AndroidVectorParser : IconParser {
        /**
         * Parses an AVG file into an [IconFileContents] object.
         *
         * The parsing procedure can be summed up as follows:
         * 1. Read the content of the file.
         * 2. Parses the file content into a [AvgRootNode] object.
         * 3. Converts the parsed AVG element nodes into [ImageVectorNode] to obtain
         * a list of nodes.
         * 4. Lastly, an [IconFileContents] instance is assembled with the necessary
         * data needed to create the Jetpack Compose Icon and returns it.
         *
         * @param file a [Path] object that leads to the file to be parsed into an icon.
         * @param iconName a String that will serve as the identifier for the icon that
         * will be generated after parsing the file.
         * @param config an instance of the [ParserConfig] class, which contains the
         * settings needed to parse the file into an icon.
         * @return an [IconFileContents] object instance that holds data about the parsed
         * icon.
         */
        override fun parse(
            content: String,
            iconName: String,
            config: ParserConfig,
        ): IconFileContents {
            val root = XmlParser.parse(content = content, fileType = FileType.Avg)
            val avg = root.children.single { it is AvgRootNode } as AvgRootNode
            val nodes = avg.asNodes(minified = config.minified)

            return IconFileContents(
                pkg = config.pkg,
                iconName = iconName,
                theme = config.theme,
                width = avg.width,
                height = avg.height,
                viewportWidth = avg.viewportWidth,
                viewportHeight = avg.viewportHeight,
                nodes = nodes,
                receiverType = config.receiverType,
                addToMaterial = config.addToMaterial,
                noPreview = config.noPreview,
                makeInternal = config.makeInternal,
                imports = createImports(nodes, config),
            )
        }
    }
}