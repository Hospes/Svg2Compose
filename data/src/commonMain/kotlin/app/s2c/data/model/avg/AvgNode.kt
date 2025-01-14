package app.s2c.data.model.avg

import app.s2c.data.model.ImageVectorNode
import app.s2c.data.model.delegate.attribute
import app.s2c.data.model.xml.XmlNode
import app.s2c.data.model.xml.XmlParentNode

sealed interface AvgNode : XmlNode {
    companion object {
        const val NAMESPACE = "android"
    }
}

class AvgRootNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : AvgElementNode(parent, children, attributes, tagName = TAG_NAME), AvgNode {
    val width: Float by attribute<String, _>(namespace = AvgNode.NAMESPACE) {
        it.removeSuffix("dp").toFloat()
    }
    val height: Float by attribute<String, _>(namespace = AvgNode.NAMESPACE) {
        it.removeSuffix("dp").toFloat()
    }
    val viewportWidth: Float by attribute(namespace = AvgNode.NAMESPACE)
    val viewportHeight: Float by attribute(namespace = AvgNode.NAMESPACE)

    companion object {
        const val TAG_NAME = "vector"
    }
}

fun AvgRootNode.asNodes(
    minified: Boolean,
): List<ImageVectorNode> = children.mapNotNull { node ->
    (node as? AvgNode)?.asNode(minified)
}

fun AvgNode.asNode(
    minified: Boolean,
): ImageVectorNode? = when (this) {
    is AvgGroupNode -> asNode(minified)
    is AvgPathNode -> asNode(minified)
    else -> null
}
