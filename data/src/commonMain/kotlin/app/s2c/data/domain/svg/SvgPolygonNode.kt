package app.s2c.data.domain.svg

import app.s2c.data.domain.ImageVectorNode
import app.s2c.data.domain.PathCommand
import app.s2c.data.domain.PathNodes
import app.s2c.data.domain.builder.pathNode
import app.s2c.data.domain.delegate.attribute
import app.s2c.data.domain.xml.XmlChildNode
import app.s2c.data.domain.xml.XmlParentNode
import app.s2c.data.geom.Point2D

abstract class SvgGraphicNodeWithListOfPoints<T>(
    parent: XmlParentNode,
    attributes: MutableMap<String, String>,
    tagName: String,
) : SvgGraphicNode<T>(parent, attributes, tagName)
    where T : SvgNode, T : XmlChildNode {
    private val listOfPointsRegex = Regex("[-+]?(?:\\d*\\.\\d+|\\d+\\.?)(?:[eE][-+]?\\d+)?")

    val points by attribute<String?, List<Point2D>> { points ->
        if (points.isNullOrBlank()) {
            emptyList()
        } else {
            listOfPointsRegex
                .findAll(points)
                .map { it.value }
                .windowed(size = 2, step = 2)
                .map { (x, y) -> Point2D(x.toFloat(), y.toFloat()) }
                .toList()
        }
    }
}

class SvgPolygonNode(
    parent: XmlParentNode,
    attributes: MutableMap<String, String>,
) : SvgGraphicNodeWithListOfPoints<SvgPolygonNode>(parent, attributes, TAG_NAME) {
    override val constructor: SvgChildNodeConstructorFn<SvgPolygonNode> = ::SvgPolygonNode

    companion object {
        const val TAG_NAME = "polygon"
    }
}

fun SvgPolygonNode.asNode(
    minified: Boolean,
): ImageVectorNode.Path {
    val nodes = createSimplePolygonNodes(minified)
    return ImageVectorNode.Path(
        params = ImageVectorNode.Path.Params(
            fill = fillBrush(nodes),
            fillAlpha = fillOpacity,
            pathFillType = fillRule,
            stroke = strokeBrush(nodes),
            strokeAlpha = strokeOpacity,
            strokeLineCap = strokeLineCap,
            strokeLineJoin = strokeLineJoin,
            strokeMiterLimit = strokeMiterLimit,
            strokeLineWidth = strokeWidth,
        ),
        wrapper = ImageVectorNode.NodeWrapper(
            normalizedPath = buildNormalizedPath(),
            nodes = nodes,
        ),
        minified = minified,
        transformations = transform?.toTransformations(),
    )
}

private fun SvgPolygonNode.buildNormalizedPath(): String = buildString {
    append("<polygon ")
    append("points=\"${points.joinToString(" ") { "${it.x} ${it.y}" }}\" ")
    append(graphicNodeParams())
    append("/>")
}

private fun SvgPolygonNode.createSimplePolygonNodes(
    minified: Boolean,
): List<PathNodes> = buildList {
    val points = points.toMutableList()
    val first = points.removeFirst()
    add(
        pathNode(command = PathCommand.MoveTo) {
            args(first.x, first.y)
            this.minified = minified
        },
    )
    points.forEachIndexed { index, (x, y) ->
        add(
            pathNode(command = PathCommand.LineTo) {
                args(x, y)
                this.minified = minified
                close = index == points.lastIndex
            },
        )
    }
}
