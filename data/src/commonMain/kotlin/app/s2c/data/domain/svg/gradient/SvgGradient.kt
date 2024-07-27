package app.s2c.data.domain.svg.gradient

import app.s2c.data.domain.PathNodes
import app.s2c.data.domain.compose.ComposeBrush
import app.s2c.data.domain.delegate.attribute
import app.s2c.data.domain.svg.SvgColor
import app.s2c.data.domain.svg.SvgElementNode
import app.s2c.data.domain.svg.SvgGradientStopNode
import app.s2c.data.domain.svg.SvgLength
import app.s2c.data.domain.svg.SvgNode
import app.s2c.data.domain.svg.SvgRootNode
import app.s2c.data.domain.svg.transform.SvgTransform
import app.s2c.data.domain.xml.XmlNode
import app.s2c.data.domain.xml.XmlParentNode
import app.s2c.data.geom.bounds.boundingBox
import kotlin.math.max

sealed class SvgGradient<out T>(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
    tagName: String,
) : SvgElementNode<T>(parent, children, attributes, tagName)
    where T : SvgNode, T : XmlParentNode {
    val gradientUnits: String by attribute(defaultValue = "objectBoundingBox") // <userSpaceOnUse | objectBoundingBox>
    val gradientTransform: SvgTransform? by attribute<String?, SvgTransform?> {
        it?.let(::SvgTransform)
    }
    val spreadMethod: SvgGradientSpreadMethod by attribute<String, _>(
        defaultValue = SvgGradientSpreadMethod.Pad,
    ) { spreadMethod ->
        spreadMethod.let(SvgGradientSpreadMethod.Companion::invoke)
    }
    val href: String? by attribute(name = "xlink:href")

    val colorStops: Pair<List<SvgColor>, List<Float>>
        get() {
            if (children.isEmpty()) {
                return emptyList<SvgColor>() to emptyList()
            }

            return children
                .filterIsInstance<SvgGradientStopNode>()
                .map { it.gradientColor to it.offset }
                .unzip()
        }

    internal fun calculateGradientXCoordinate(
        length: SvgLength,
        target: List<PathNodes> = emptyList(),
    ): Float {
        val root = rootParent as SvgRootNode
        return if (gradientUnits == "objectBoundingBox") {
            check(target.isNotEmpty())
            val boundingBox = target.boundingBox()
            length.toFloat(boundingBox.width.toFloat()) + boundingBox.x.toFloat()
        } else {
            val baseDimension = root.viewportWidth
            length.toFloat(baseDimension)
        }
    }

    internal fun calculateGradientYCoordinate(
        length: SvgLength,
        target: List<PathNodes> = emptyList(),
    ): Float {
        val root = rootParent as SvgRootNode
        return if (gradientUnits == "objectBoundingBox") {
            check(target.isNotEmpty())
            val boundingBox = target.boundingBox()
            length.toFloat(boundingBox.height.toFloat()) + boundingBox.y.toFloat()
        } else {
            val baseDimension = root.viewportHeight
            length.toFloat(baseDimension)
        }
    }

    internal fun calculateGradientXYCoordinate(
        length: SvgLength,
        target: List<PathNodes> = emptyList(),
    ): Float {
        val root = rootParent as SvgRootNode
        return if (gradientUnits == "objectBoundingBox") {
            check(target.isNotEmpty())
            val boundingBox = target.boundingBox()
            length.toFloat(max(boundingBox.width, boundingBox.height).toFloat()) +
                max(boundingBox.x, boundingBox.y).toFloat()
        } else {
            val baseDimension = max(root.viewportWidth, root.viewportHeight)
            length.toFloat(baseDimension)
        }
    }

    abstract fun toBrush(
        target: List<PathNodes>,
    ): ComposeBrush.Gradient
}
