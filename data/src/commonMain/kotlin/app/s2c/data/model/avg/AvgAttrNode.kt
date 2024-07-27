package app.s2c.data.model.avg

import app.s2c.data.model.delegate.attribute
import app.s2c.data.model.xml.XmlNode
import app.s2c.data.model.xml.XmlParentNode

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
