package app.s2c.data.domain.avg

import app.s2c.data.domain.xml.XmlElementNode
import app.s2c.data.domain.xml.XmlNode
import app.s2c.data.domain.xml.XmlParentNode

abstract class AvgElementNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
    override val tagName: String,
) : XmlElementNode(parent, children, attributes, tagName), AvgNode
