package app.s2c.data.domain.svg

import app.s2c.data.domain.svg.SvgElementNode
import app.s2c.data.domain.svg.SvgNode
import app.s2c.data.domain.xml.XmlNode
import app.s2c.data.domain.xml.XmlParentNode

class SvgSymbolNode(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    override val attributes: MutableMap<String, String>,
) : SvgElementNode<SvgSymbolNode>(parent, children, attributes, tagName = TAG_NAME), SvgNode {
    override val constructor = ::SvgSymbolNode
    companion object {
        const val TAG_NAME = "symbol"
    }
}
