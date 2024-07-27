package app.s2c.data.domain.avg

import app.s2c.data.domain.ImageVectorNode
import app.s2c.data.domain.asNodeWrapper
import app.s2c.data.domain.delegate.attribute
import app.s2c.data.domain.xml.XmlNode
import app.s2c.data.domain.xml.XmlParentNode

class AvgGroupNode(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : AvgElementNode(parent, children, attributes, tagName = TAG_NAME), AvgNode {
    val clipPath: AvgClipPath?
        get() = children.firstOrNull { it is AvgClipPath } as? AvgClipPath
    val rotation: Float? by attribute(namespace = AvgNode.NAMESPACE)
    val pivotX: Float? by attribute(namespace = AvgNode.NAMESPACE)
    val pivotY: Float? by attribute(namespace = AvgNode.NAMESPACE)
    val translateX: Float? by attribute(namespace = AvgNode.NAMESPACE)
    val translateY: Float? by attribute(namespace = AvgNode.NAMESPACE)
    val scaleX: Float? by attribute(namespace = AvgNode.NAMESPACE)
    val scaleY: Float? by attribute(namespace = AvgNode.NAMESPACE)

    companion object {
        const val TAG_NAME = "group"
    }
}

fun AvgGroupNode.asNode(
    minified: Boolean,
): ImageVectorNode.Group = ImageVectorNode.Group(
    params = ImageVectorNode.Group.Params(
        clipPath = clipPath?.pathData?.asNodeWrapper(minified),
        rotate = rotation,
        pivotX = pivotX,
        pivotY = pivotY,
        scaleX = scaleX,
        scaleY = scaleY,
        translationX = translateX,
        translationY = translateY,
    ),
    commands = children.mapNotNull { (it as? AvgNode)?.asNode(minified) },
    minified = minified,
)
