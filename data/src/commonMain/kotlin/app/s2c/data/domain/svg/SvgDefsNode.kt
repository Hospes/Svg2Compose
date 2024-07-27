package app.s2c.data.domain.svg

import app.s2c.data.domain.xml.XmlNode
import app.s2c.data.domain.xml.XmlParentNode

class SvgDefsNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgElementNode<SvgDefsNode>(parent, children, attributes, tagName = TAG_NAME), SvgNode {
    override val constructor = ::SvgDefsNode
    companion object {
        const val TAG_NAME = "defs"
    }
}
