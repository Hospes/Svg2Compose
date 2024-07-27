package app.s2c.data.model.avg

import app.s2c.data.model.xml.XmlChildNode
import app.s2c.data.model.xml.XmlParentNode

abstract class AvgChildNode(parent: XmlParentNode) : XmlChildNode(parent), AvgNode {
    override fun toString(): String = toJsString()
}
