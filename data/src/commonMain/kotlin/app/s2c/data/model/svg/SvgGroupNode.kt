package app.s2c.data.model.svg

import app.s2c.data.model.ImageVectorNode
import app.s2c.data.model.asNodeWrapper
import app.s2c.data.model.delegate.attribute
import app.s2c.data.model.xml.XmlNode
import app.s2c.data.model.xml.XmlParentNode
import app.s2c.data.extensions.firstInstanceOfOrNull

class SvgGroupNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgElementNode<SvgGroupNode>(parent, children, attributes, tagName = TAG_NAME), SvgNode {
    override val constructor = ::SvgGroupNode
    val maskId: String? by attribute("mask")
    val filterId: String? by attribute("filter")
    val opacity: Float? by attribute()
    val fillOpacity: Float? by attribute("fill-opacity")
    val style: String? by attribute()
    val clipPath: SvgClipPath? by lazy {
        children.firstInstanceOfOrNull()
    }

    companion object {
        const val TAG_NAME = "g"
    }
}

private fun SvgGroupNode.asNode(
    masks: List<SvgMaskNode>,
    minified: Boolean = false,
): ImageVectorNode.Group {
    // Can a svg mask have more than one path?
    // Currently, a group on ImageVector only receives a single PathData as a parameter.
    // Not sure if it would support multiple path tags inside a mask from a svg.
    val clipPath = createGroupClipPath(masks, minified)

    return ImageVectorNode.Group(
        params = ImageVectorNode.Group.Params(
            clipPath = clipPath,
        ),
        commands = children
            .mapNotNull { (it as? SvgNode)?.asNodes(masks, minified) }
            .flatten(),
        minified = minified,
        transformations = transform?.toTransformations(),
    )
}

fun SvgGroupNode.flatNode(
    masks: List<SvgMaskNode>,
    minified: Boolean,
): List<ImageVectorNode> {
    val node = asNode(masks, minified)
    return if (node.params.isEmpty()) {
        node.commands
    } else {
        listOf(node)
    }
}

/**
 * Creates a group clip path from the given masks.
 *
 * @param masks The list of masks.
 * @param minified Whether to minify the output.
 * @return The group clip path, or null if no mask with the given ID is found.
 */
private fun SvgGroupNode.createGroupClipPath(
    masks: List<SvgMaskNode>,
    minified: Boolean,
): ImageVectorNode.NodeWrapper? = masks
    .firstOrNull { it.id == maskId?.normalizedId() }
    ?.path
    ?.d
    ?.asNodeWrapper(minified)
    ?: clipPath?.asNodeWrapper(minified = true)
