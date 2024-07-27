package app.s2c.data.domain.avg

import app.s2c.data.domain.delegate.attribute
import app.s2c.data.domain.xml.XmlNode
import app.s2c.data.domain.xml.XmlParentNode

class AvgAttrNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : AvgElementNode(parent, children, attributes, tagName = TAG_NAME), AvgNode {
    val name: String by attribute()

    companion object {
        const val TAG_NAME = "aapt:attr"
    }
}
