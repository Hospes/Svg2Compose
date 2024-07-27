package app.s2c.data.model.svg

import app.s2c.data.model.xml.XmlNode
import app.s2c.data.model.xml.XmlParentNode

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
