package app.s2c.data.model.avg

import app.s2c.data.model.xml.XmlElementNode
import app.s2c.data.model.xml.XmlNode
import app.s2c.data.model.xml.XmlParentNode

abstract class AvgElementNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
    override val tagName: String,
) : XmlElementNode(parent, children, attributes, tagName), AvgNode
