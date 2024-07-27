package app.s2c.data.domain.avg

import app.s2c.data.domain.delegate.attribute
import app.s2c.data.domain.xml.XmlParentNode

class AvgClipPath(
    parent: XmlParentNode,
    override val attributes: MutableMap<String, String>,
) : AvgChildNode(parent), AvgNode {
    override val tagName: String = TAG_NAME
    val pathData: String by attribute(namespace = AvgNode.NAMESPACE)

    companion object {
        const val TAG_NAME = "clip-path"
    }
}
