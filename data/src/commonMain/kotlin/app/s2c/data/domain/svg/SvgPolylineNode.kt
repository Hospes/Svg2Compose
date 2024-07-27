package app.s2c.data.domain.svg

import app.s2c.data.domain.ImageVectorNode
import app.s2c.data.domain.PathCommand
import app.s2c.data.domain.PathNodes
import app.s2c.data.domain.builder.pathNode
import app.s2c.data.domain.xml.XmlParentNode

class SvgPolylineNode(
    parent: XmlParentNode,
    attributes: MutableMap<String, String>,
) : SvgGraphicNodeWithListOfPoints<SvgPolylineNode>(parent, attributes, TAG_NAME) {
    override val constructor: SvgChildNodeConstructorFn<SvgPolylineNode> = ::SvgPolylineNode

    companion object {
        const val TAG_NAME = "polyline"
    }
}

fun SvgPolylineNode.asNode(
    minified: Boolean,
): ImageVectorNode.Path {
    val nodes = createSimplePolylineNodes(minified)
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

private fun SvgPolylineNode.buildNormalizedPath(): String = buildString {
    append("<polyline ")
    append("points=\"${points.joinToString(" ") { "${it.x} ${it.y}" }}\" ")
    append(graphicNodeParams())
    append("/>")
}

private fun SvgPolylineNode.createSimplePolylineNodes(
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
    points.forEach { (x, y) ->
        add(
            pathNode(command = PathCommand.LineTo) {
                args(x, y)
                this.minified = minified
            },
        )
    }
}
