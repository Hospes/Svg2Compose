package app.s2c.data.domain.svg.gradient

import app.s2c.data.domain.delegate.attribute
import app.s2c.data.domain.svg.SvgLength
import app.s2c.data.domain.svg.SvgNode
import app.s2c.data.domain.xml.XmlNode
import app.s2c.data.domain.xml.XmlParentNode

abstract class SvgLinearGradient<out T>(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgGradient<T>(parent, children, attributes, tagName = TAG_NAME)
    where T : SvgNode, T : XmlParentNode {
    /**
     * [X1Attribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-LinearGradientElementX1Attribute)
     */
    internal val x1: SvgLength by attribute(defaultValue = SvgLength("0%"))

    /**
     * [Y1Attribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-LinearGradientElementY1Attribute)
     */
    internal val y1: SvgLength by attribute(defaultValue = SvgLength("0%"))

    /**
     * [X2Attribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-LinearGradientElementX2Attribute)
     */
    internal val x2: SvgLength by attribute(defaultValue = SvgLength("100%"))

    /**
     * [Y2Attribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-LinearGradientElementY2Attribute)
     */
    internal val y2: SvgLength by attribute(defaultValue = SvgLength("100%"))

    companion object {
        const val TAG_NAME = "linearGradient"
    }
}
