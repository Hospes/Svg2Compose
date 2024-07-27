package app.s2c.data.domain.svg.gradient

import app.s2c.data.domain.delegate.attribute
import app.s2c.data.domain.svg.gradient.SvgGradient
import app.s2c.data.domain.svg.SvgLength
import app.s2c.data.domain.svg.SvgNode
import app.s2c.data.domain.xml.XmlNode
import app.s2c.data.domain.xml.XmlParentNode

abstract class SvgRadialGradient<out T>(
    parent: XmlParentNode,
    override val children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgGradient<T>(parent, children, attributes, tagName = TAG_NAME)
    where T : SvgNode, T : XmlParentNode {
    /**
     * [CXAttribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-RadialGradientElementCXAttribute)
     */
    internal val cx: SvgLength by attribute(defaultValue = SvgLength("50%"))

    /**
     * [CYAttribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-RadialGradientElementCYAttribute)
     */
    internal val cy: SvgLength by attribute(defaultValue = SvgLength("50%"))

    /**
     * [RAttribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-RadialGradientElementRAttribute)
     */
    internal val radius: SvgLength by attribute<SvgLength?, SvgLength>(
        name = "r",
        transform = { length -> length ?: SvgLength("50%") }
    )

    /**
     * [FXAttribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-RadialGradientElementFXAttribute)
     */
    internal val fx: SvgLength by attribute(defaultValue = cx)

    /**
     * [FYAttribute](https://www.w3.org/TR/SVG11/single-page.html#pservers-RadialGradientElementFYAttribute)
     */
    internal val fy: SvgLength by attribute(defaultValue = cy)

    companion object {
        const val TAG_NAME = "radialGradient"
    }
}
