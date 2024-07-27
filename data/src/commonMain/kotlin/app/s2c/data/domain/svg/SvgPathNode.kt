package app.s2c.data.domain.svg

import app.s2c.data.domain.ImageVectorNode
import app.s2c.data.domain.PathNodes
import app.s2c.data.domain.asNodeWrapper
import app.s2c.data.domain.compose.ComposeBrush
import app.s2c.data.domain.compose.toBrush
import app.s2c.data.domain.delegate.attribute
import app.s2c.data.domain.xml.XmlParentNode

class SvgPathNode(
    parent: XmlParentNode,
    attributes: MutableMap<String, String>,
) : SvgGraphicNode<SvgPathNode>(parent, attributes, TAG_NAME), SvgNode {
    override val constructor = ::SvgPathNode
    val d: String by attribute()
    val clipPath: String? by attribute(name = "clip-path")

    val fillBrush: ComposeBrush? by lazy {
        fill?.value?.let { fill ->
            if (fill.startsWith("url(")) {
                getGradient(fill)
            } else {
                fill.toBrush()
            }
        }
    }

    val strokeBrush: ComposeBrush? by lazy {
        stroke?.value?.let { stroke ->
            if (stroke.startsWith("url(")) {
                getGradient(stroke)
            } else {
                stroke.toBrush()
            }
        }
    }

    override fun fillBrush(nodes: List<PathNodes>): ComposeBrush? {
        error("use fillBrush property instead")
    }

    override fun strokeBrush(nodes: List<PathNodes>): ComposeBrush? {
        error("use strokeBrush property instead")
    }

    private fun getGradient(fillColor: String): ComposeBrush.Gradient? {
        return getGradient(
            fillColor = fillColor,
            nodes = d.asNodeWrapper(minified = false).nodes,
        )
    }

    companion object {
        const val TAG_NAME = "path"
    }
}

fun SvgPathNode.asNode(
    minified: Boolean,
): ImageVectorNode {
    val path = ImageVectorNode.Path(
        params = ImageVectorNode.Path.Params(
            fill = fillBrush,
            fillAlpha = fillOpacity,
            pathFillType = fillRule,
            stroke = strokeBrush,
            strokeAlpha = strokeOpacity,
            strokeLineCap = strokeLineCap,
            strokeLineJoin = strokeLineJoin,
            strokeMiterLimit = strokeMiterLimit,
            strokeLineWidth = strokeWidth,
        ),
        wrapper = d.asNodeWrapper(minified),
        minified = minified,
        transformations = transform?.toTransformations(),
    )

    return clipPath?.normalizedId()?.let { clipPathId ->
        val clipPath = rootParent
            .children
            .find { it is SvgClipPath && it.id == clipPathId } as? SvgClipPath
        clipPath?.let {
            ImageVectorNode.Group(
                params = ImageVectorNode.Group.Params(
                    clipPath = it.asNodeWrapper(minified),
                ),
                commands = listOf(path),
                minified = minified,
                transformations = transform?.toTransformations(),
            )
        }
    } ?: path
}
