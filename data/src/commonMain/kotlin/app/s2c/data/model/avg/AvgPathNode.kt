package app.s2c.data.model.avg

import app.s2c.data.model.ImageVectorNode
import app.s2c.data.model.asNodeWrapper
import app.s2c.data.model.avg.*
import app.s2c.data.model.compose.*
import app.s2c.data.model.delegate.attribute
import app.s2c.data.model.xml.XmlNode
import app.s2c.data.model.xml.XmlParentNode
import app.s2c.data.extensions.firstInstanceOfOrNull
import app.s2c.data.extensions.toLengthFloat

class AvgPathNode(
    parent: XmlParentNode,
    children: MutableSet<XmlNode>,
    attributes: MutableMap<String, String>,
) : AvgElementNode(parent, children, attributes, TAG_NAME), AvgNode {
    val pathData: String by attribute(namespace = AvgNode.NAMESPACE)

    private val fillColor: String? by attribute(namespace = AvgNode.NAMESPACE)

    val fillAlpha: Float? by attribute(namespace = AvgNode.NAMESPACE)

    val fillType: PathFillType? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        it?.let(PathFillType::invoke)
    } // evenOdd, nonZero

    private val strokeColor: String? by attribute(namespace = AvgNode.NAMESPACE)

    // Although Android Vector only accepts Float, Svg accepts %.
    // As we still parse the SVG to XML after optimizing, we need to deal with % here too.
    val strokeAlpha: Float? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        (parent as? AvgRootNode)?.let { parent -> it?.toLengthFloat(parent.width, parent.height) }
    }

    val strokeLineCap: StrokeCap? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        it?.let(StrokeCap::invoke)
    } // butt, round, square

    val strokeLineJoin: StrokeJoin? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        it?.let(StrokeJoin::invoke)
    } // miter, round, bevel

    val strokeMiterLimit: Float? by attribute(namespace = AvgNode.NAMESPACE)

    // Although Android Vector only accepts Float, Svg accepts %.
    // As we still parse the SVG to XML after optimizing, we need to deal with % here too.
    val strokeWidth: Float? by attribute<String?, _>(namespace = AvgNode.NAMESPACE) {
        (parent as? AvgRootNode)?.let { parent -> it?.toLengthFloat(parent.width, parent.height) }
    }

    val fillBrush: ComposeBrush? by lazy {
        val fillColor = fillColor

        if (fillColor.isNullOrBlank()) {
            getGradient(propertyName = "fillColor")
        } else {
            fillColor.toBrush()
        }
    }

    val strokeBrush: ComposeBrush? by lazy {
        val strokeColor = strokeColor

        if (strokeColor.isNullOrBlank()) {
            getGradient(propertyName = "strokeColor")
        } else {
            strokeColor.toBrush()
        }
    }

    private fun getGradient(propertyName: String): ComposeBrush.Gradient? =
        children
            .asSequence()
            .filterIsInstance<AvgAttrNode>()
            .filter { it.name == "${AvgNode.NAMESPACE}:$propertyName" }
            .firstOrNull()
            ?.children
            ?.firstInstanceOfOrNull<AvgGradientNode>()
            ?.toBrush()

    companion object {
        const val TAG_NAME = "path"
    }
}

fun AvgPathNode.asNode(
    minified: Boolean,
): ImageVectorNode.Path = ImageVectorNode.Path(
    params = ImageVectorNode.Path.Params(
        fill = fillBrush,
        fillAlpha = fillAlpha,
        pathFillType = fillType,
        stroke = strokeBrush,
        strokeAlpha = strokeAlpha,
        strokeLineCap = strokeLineCap,
        strokeLineJoin = strokeLineJoin,
        strokeMiterLimit = strokeMiterLimit,
        strokeLineWidth = strokeWidth,
    ),
    wrapper = pathData.asNodeWrapper(minified),
    minified = minified,
)
