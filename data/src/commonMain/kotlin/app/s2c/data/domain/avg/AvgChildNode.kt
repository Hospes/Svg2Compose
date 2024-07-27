package app.s2c.data.domain.avg

import app.s2c.data.domain.xml.XmlChildNode
import app.s2c.data.domain.xml.XmlParentNode

abstract class AvgChildNode(parent: XmlParentNode) : XmlChildNode(parent), AvgNode {
    override fun toString(): String = toJsString()
}
