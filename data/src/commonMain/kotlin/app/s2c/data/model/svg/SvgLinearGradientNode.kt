package app.s2c.data.model.svg

import app.s2c.data.model.PathNodes
import app.s2c.data.model.compose.ComposeBrush
import app.s2c.data.model.compose.ComposeOffset
import app.s2c.data.model.svg.gradient.SvgLinearGradient
import app.s2c.data.model.xml.XmlNode
import app.s2c.data.model.xml.XmlParentNode

class SvgLinearGradientNode(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : SvgLinearGradient<SvgLinearGradientNode>(parent, children, attributes), SvgNode {
    override val constructor = ::SvgLinearGradientNode

    override fun toBrush(
        target: List<PathNodes>,
    ): ComposeBrush.Gradient.Linear {
        val (colors, stops) = colorStops

        val startOffset = ComposeOffset(
            x = calculateGradientXCoordinate(x1, target),
            y = calculateGradientXYCoordinate(y1, target),
        )

        val endOffset = ComposeOffset(
            x = calculateGradientXCoordinate(x2, target),
            y = calculateGradientYCoordinate(y2, target),
        )

        return ComposeBrush.Gradient.Linear(
            start = startOffset,
            end = endOffset,
            tileMode = spreadMethod.toCompose(),
            colors = colors.map { it.toComposeColor() },
            stops = stops,
        )
    }
}
