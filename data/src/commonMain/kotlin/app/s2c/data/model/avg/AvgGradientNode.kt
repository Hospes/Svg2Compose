package app.s2c.data.model.avg

import app.s2c.data.model.avg.gradient.*
import app.s2c.data.model.compose.ComposeBrush
import app.s2c.data.model.compose.ComposeOffset
import app.s2c.data.model.compose.GradientTileMode
import app.s2c.data.model.delegate.attribute
import app.s2c.data.model.xml.XmlNode
import app.s2c.data.model.xml.XmlParentNode

class AvgGradientNode(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : AvgElementNode(parent, children, attributes, tagName = AvgGradient.TAG_NAME),
    AvgNode,
    AvgLinearGradient,
    AvgRadianGradient,
    AvgSweepGradient {
    override val gradientRadius: Float? by attribute(namespace = AvgNode.NAMESPACE)
    override val centerX: Float? by attribute(namespace = AvgNode.NAMESPACE)
    override val centerY: Float? by attribute(namespace = AvgNode.NAMESPACE)
    override val startX: Float? by attribute(namespace = AvgNode.NAMESPACE)
    override val startY: Float? by attribute(namespace = AvgNode.NAMESPACE)
    override val endX: Float? by attribute(namespace = AvgNode.NAMESPACE)
    override val endY: Float? by attribute(namespace = AvgNode.NAMESPACE)
    override val tileMode: AvgGradientTileMode? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) { tileMode ->
        tileMode?.let(AvgGradientTileMode::invoke)
    }
    override val startColor: AvgColor? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        it?.let(::AvgColor)
    }
    override val centerColor: AvgColor? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        it?.let(::AvgColor)
    }
    override val endColor: AvgColor? by attribute(namespace = AvgNode.NAMESPACE)
    override val type: AvgGradientType? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) { tileMode ->
        tileMode?.let { AvgGradientType(it) }
    }

    val items: Set<AvgGradientItemNode> by lazy {
        children.filterIsInstance<AvgGradientItemNode>().toSet()
    }

    override fun toString(): String {
        return super.toString()
    }
}

fun AvgGradientNode.toBrush(): ComposeBrush.Gradient? = when (type) {
    AvgGradientType.Linear -> buildLinearGradient()
    AvgGradientType.Radial -> buildRadialGradient()
    AvgGradientType.Sweep -> buildSweepGradient()
    null -> null
}

fun AvgGradientNode.buildLinearGradient(): ComposeBrush.Gradient.Linear {
    val startX = startX ?: 0f
    val startY = startY ?: 0f
    val (colors, stops) = getColorStops()

    if (colors.isEmpty()) {
        error("Missing gradient colors.")
    }
    val startOffset = ComposeOffset(x = startX, y = startY)
    val endOffset = ComposeOffset(x = endX, y = endY)

    return ComposeBrush.Gradient.Linear(
        start = startOffset,
        end = endOffset,
        tileMode = GradientTileMode(tileMode?.name),
        colors = colors.map { it.toComposeColor() },
        stops = stops,
    )
}

fun AvgGradientNode.buildRadialGradient(): ComposeBrush.Gradient.Radial {
    val (colors, stops) = getColorStops()

    if (colors.isEmpty()) {
        error("Missing gradient colors.")
    }
    val centerOffset = ComposeOffset(x = centerX, y = centerY)

    return ComposeBrush.Gradient.Radial(
        center = centerOffset,
        tileMode = GradientTileMode(tileMode?.name),
        colors = colors.map { it.toComposeColor() },
        stops = stops,
        radius = gradientRadius,
    )
}

fun AvgGradientNode.buildSweepGradient(): ComposeBrush.Gradient.Sweep {
    val (colors, stops) = getColorStops()

    if (colors.isEmpty()) {
        error("Missing gradient colors.")
    }
    val centerOffset = ComposeOffset(x = centerX, y = centerY)

    return ComposeBrush.Gradient.Sweep(
        center = centerOffset,
        colors = colors.map { it.toComposeColor() },
        stops = stops,
    )
}

private fun AvgGradientNode.getColorStops(): Pair<List<AvgColor>, List<Float>> {
    val gradientItems = children
        .asSequence()
        .filterIsInstance<AvgGradientItemNode>()

    val colors = if (children.isEmpty()) {
        listOf(startColor, endColor).mapNotNull { it }
    } else {
        gradientItems
            .mapNotNull { it.color }
            .toList()
    }
    val stops = gradientItems
        .mapNotNull { it.offset }
        .toList()
    return Pair(colors, stops)
}
