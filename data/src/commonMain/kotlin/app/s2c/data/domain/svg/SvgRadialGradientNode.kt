package app.s2c.data.domain.svg

import app.s2c.data.domain.PathNodes
import app.s2c.data.domain.compose.ComposeBrush
import app.s2c.data.domain.compose.ComposeOffset
import app.s2c.data.domain.svg.SvgNode
import app.s2c.data.domain.svg.toComposeColor
import app.s2c.data.domain.svg.gradient.SvgRadialGradient
import app.s2c.data.domain.xml.XmlNode
import app.s2c.data.domain.xml.XmlParentNode

class SvgRadialGradientNode(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgRadialGradient<SvgRadialGradientNode>(parent, children, attributes), SvgNode {
    override val constructor = ::SvgRadialGradientNode

    override fun toBrush(
        target: List<PathNodes>,
    ): ComposeBrush.Gradient.Radial {
        val (colors, stops) = colorStops

        val cx = calculateGradientXCoordinate(cx, target)
        val cy = calculateGradientYCoordinate(cy, target)

        val centerOffset = ComposeOffset(x = cx, y = cy)
        val gradientRadius = calculateGradientXYCoordinate(radius, target)

        return ComposeBrush.Gradient.Radial(
            center = centerOffset,
            tileMode = spreadMethod.toCompose(),
            colors = colors.map { it.toComposeColor() },
            stops = stops,
            radius = gradientRadius,
        )
    }
}
