package app.s2c.data.parser

import app.s2c.data.model.FileType
import app.s2c.data.model.svg.*
import app.s2c.data.model.svg.SvgNode.Companion.normalizedId
import app.s2c.data.model.svg.gradient.SvgLinearGradient
import app.s2c.data.model.svg.gradient.SvgRadialGradient
import app.s2c.data.model.xml.XmlChildNode
import app.s2c.data.model.xml.XmlNode
import app.s2c.data.model.xml.XmlParentNode
import app.s2c.data.model.xml.XmlPendingParentElement
import app.s2c.data.error.ErrorCode
import app.s2c.data.error.ParserException
import com.fleeksoft.ksoup.helper.ValidationException
import com.fleeksoft.ksoup.nodes.Attributes
import com.fleeksoft.ksoup.nodes.Element
import com.fleeksoft.ksoup.nodes.Node

/**
 * A parser for SVG (Scalable Vector Graphics) files.
 *
 * This parser extends the [XmlParser] class and provides custom handling for SVG elements.
 */
internal class SvgParser : XmlParser() {
    override val fileType: FileType = FileType.Svg
    override fun createElement(node: Element, parent: XmlParentNode): XmlNode = createSvgElement(
        nodeName = node.tagName(),
        attributes = node.attributes(),
        parent = parent,
        rootElement = rootNode,
        elementsWithId = elementsWithId,
        elementsPendingParent = elementsPendingParent,
        root = root as? SvgRootNode,
    )

    override fun XmlNode.isRootNode(): Boolean =
        this is SvgRootNode

    override fun getPreProcessedElement(node: Node, parent: XmlParentNode): XmlNode? = elementsPendingParent
        .find { it.id == node.attributes()["id"] }
        ?.apply {
            if (this is XmlChildNode) {
                attachParentIfNeeded(parent)
                elementsPendingParent.remove(this)
            }
        }

    /**
     * Creates an [XmlNode] from the given SVG element.
     *
     * @param nodeName The name of the SVG element.
     * @param attributes The attributes of the SVG element.
     * @param parent The parent node of the SVG element.
     * @param rootElement The root element of the SVG document.
     * @param elementsWithId A set of nodes that have an ID attribute.
     * @param elementsPendingParent A set of nodes that are waiting for their parent to be created.
     * @param root The root node of the SVG document, or null if the document has no root node.
     * @return The created [XmlNode].
     * @suppress `CyclomaticComplexMethod` is catch because of the `when` expression, which in
     * this case can't be avoided.
     */
    @Suppress("CyclomaticComplexMethod")
    private fun createSvgElement(
        nodeName: String,
        attributes: Attributes,
        parent: XmlParentNode,
        rootElement: Element,
        elementsWithId: MutableSet<XmlNode>,
        elementsPendingParent: MutableSet<XmlNode>,
        root: SvgRootNode?,
    ): XmlNode = when (nodeName) {
        SvgRootNode.TAG_NAME -> SvgRootNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgPathNode.TAG_NAME -> SvgPathNode(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgRectNode.TAG_NAME -> SvgRectNode(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgGroupNode.TAG_NAME -> SvgGroupNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgMaskNode.TAG_NAME -> SvgMaskNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgCircleNode.TAG_NAME -> SvgCircleNode(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgClipPath.TAG_NAME -> SvgClipPath(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgDefsNode.TAG_NAME -> SvgDefsNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgLinearGradient.TAG_NAME -> SvgLinearGradientNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        ).also { it.id?.let { id -> root?.gradients?.put(id, it) } }

        SvgRadialGradient.TAG_NAME -> SvgRadialGradientNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        ).also { it.id?.let { id -> root?.gradients?.put(id, it) } }

        SvgGradientStopNode.TAG_NAME -> SvgGradientStopNode(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgUseNode.TAG_NAME -> SvgUseNode(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
            replacement = findReplacementNode(
                href = attributes[SvgUseNode.HREF_ATTR_KEY].takeIf { it.isNotEmpty() }
                    ?: attributes[SvgUseNode.HREF_ATTR_NO_NS_KEY],
                rootElement = rootElement,
                useNodeAttrs = attributes,
                elementsWithId = elementsWithId,
                elementsPendingParent = elementsPendingParent,
                root = root,
                parent = parent,
            ),
        ).also { useNode ->
            root?.apply {
                if (useNode.href !in defs) {
                    defs.getOrPut(useNode.href) { useNode }
                }
            }
        }

        SvgSymbolNode.TAG_NAME -> SvgSymbolNode(
            parent = parent,
            children = mutableSetOf(),
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgPolygonNode.TAG_NAME -> SvgPolygonNode(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgPolylineNode.TAG_NAME -> SvgPolylineNode(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        SvgEllipseNode.TAG_NAME -> SvgEllipseNode(
            parent = parent,
            attributes = attributes.associate { it.key to it.value }.toMutableMap(),
        )

        else -> createDefaultElement(nodeName, attributes, parent)
    }

    /**
     * Finds the replacement node for a given SVG `<use>` element.
     *
     * @param href The href attribute of the use element, which should reference an
     * existing element in the SVG document.
     * @param rootElement The root element of the SVG document.
     * @param useNodeAttrs The attributes of the use element.
     * @param elementsWithId A set of all elements in the SVG document that have an
     * ID attribute.
     * @param elementsPendingParent A set of all elements in the SVG document that
     * are waiting for their parent element to be parsed.
     * @param root The root node of the SVG document, or null if the document is
     * not yet parsed.
     * @param parent The parent node of the use element.
     * @return The replacement node for the use element, which is a group node
     * containing the referenced element.
     */
    private fun findReplacementNode(
        href: String,
        rootElement: Element,
        useNodeAttrs: Attributes,
        elementsWithId: MutableSet<XmlNode>,
        elementsPendingParent: MutableSet<XmlNode>,
        root: SvgRootNode?,
        parent: XmlParentNode,
    ): SvgGroupNode {
        check(elementsWithId.isEmpty() || (elementsWithId.isNotEmpty() && elementsWithId.first() is SvgNode)) {
            "${::findReplacementNode.name}() should only be called in the context of Svg parsing."
        }

        val processedNode = elementsWithId
            .find {
                with(SvgNode) {
                    // As we are processing SVGs, it is safe to say this element is an SvgNode.
                    val id = it.id
                    requireNotNull(id) {
                        buildString {
                            appendLine("Something is not correct. All elements in this list must have ID.")
                            appendLine("Element: $it")
                        }
                    }
                    href.normalizedId() == id.normalizedId()
                }
            } as? SvgNode

        val replacement = try {
            processedNode ?: rootElement.getElementById(href.normalizedId())?.let { node ->
                createSvgElement(
                    nodeName = node.tagName(),
                    attributes = node.attributes(),
                    parent = XmlPendingParentElement,
                    rootElement = rootElement,
                    elementsWithId = elementsWithId,
                    elementsPendingParent = elementsPendingParent,
                    root = root,
                ).also {
                    elementsWithId += it
                    elementsPendingParent += it
                } as SvgNode
            } ?: error("Missing element with id '$href' on SVG tree.")
        } catch (e: ValidationException) {
            throw ParserException(
                errorCode = ErrorCode.ParseSvgError,
                message = "Failed to find a replacement for <use> element. The ID of the element is null.",
                cause = e
            )
        }

        return SvgUseNode.createReplacementGroupNode(useNodeAttrs, replacement, parent)
    }
}