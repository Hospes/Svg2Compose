package app.s2c.data.parser

import app.s2c.data.model.FileType
import app.s2c.data.model.avg.*
import app.s2c.data.model.avg.gradient.AvgGradient
import app.s2c.data.model.xml.XmlNode
import app.s2c.data.model.xml.XmlParentNode
import com.fleeksoft.ksoup.nodes.Attributes
import com.fleeksoft.ksoup.nodes.Element

/**
 * A parser for AVG (Android Vector Graphics) files.
 *
 * This parser extends the [XmlParser] class and provides custom handling for AVG elements.
 */
internal class AvgParser : XmlParser() {
    override val fileType = FileType.Avg

    override fun XmlNode.isRootNode(): Boolean =
        this is AvgRootNode

    override fun createElement(
        node: Element,
        parent: XmlParentNode,
    ): XmlNode = createAvgElement(
        nodeName = node.tagName(),
        attributes = node.attributes(),
        parent = parent,
    )

    /**
     * Creates an [XmlNode] from the given AVG element.
     *
     * @param nodeName The name of the AVG element.
     * @param attributes The attributes of the AVG element.
     * @param parent The parent node of the AVG element.
     * @return The created [XmlNode].
     */
    private fun createAvgElement(
        nodeName: String,
        attributes: Attributes,
        parent: XmlParentNode,
    ): XmlNode = when (nodeName) {
        AvgRootNode.TAG_NAME -> AvgRootNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        AvgPathNode.TAG_NAME -> AvgPathNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        AvgClipPath.TAG_NAME -> AvgClipPath(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        AvgGroupNode.TAG_NAME -> AvgGroupNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        AvgAttrNode.TAG_NAME -> AvgAttrNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        AvgGradient.TAG_NAME -> AvgGradientNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        AvgGradientItemNode.TAG_NAME -> AvgGradientItemNode(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        else -> createDefaultElement(nodeName, attributes, parent)
    }
}