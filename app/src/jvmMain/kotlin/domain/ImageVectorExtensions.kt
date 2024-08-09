package domain

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import app.s2c.data.model.IconFileContents
import app.s2c.data.model.ImageVectorNode
import app.s2c.data.model.PathNodes
import app.s2c.data.model.compose.ComposeBrush
import app.s2c.data.model.compose.ComposeColor

fun IconFileContents.toImageVector(): ImageVector {
    return ImageVector.Builder(
        defaultWidth = width.dp,
        defaultHeight = height.dp,
        viewportWidth = viewportWidth,
        viewportHeight = viewportHeight,
    ).apply {
        nodes.forEach { node ->
            when (node) {
//                path(
//                    fill = SolidColor(pathFillColor),
//                    fillAlpha = pathFillAlpha,
//                    stroke = pathStrokeColor?.let { strokeColor -> SolidColor(strokeColor) },
//                    strokeAlpha = pathStrokeAlpha,
//                    strokeLineWidth = pathStrokeLineWidth,
//                    strokeLineCap = pathStrokeLineCap,
//                    strokeLineJoin = pathStrokeLineJoin,
//                    strokeLineMiter = pathStrokeLineMiter,
//                    pathFillType = pathFillType,
//                    pathBuilder = getPathBuilder(path),
//                )
                is ImageVectorNode.Path -> path(
                    fill = node.params.fill?.toBrush(),
                    pathBuilder = node.wrapper.nodes.toPathBuilder(),
                )

                is ImageVectorNode.Group -> TODO()
                is ImageVectorNode.ChunkFunction -> TODO()
            }
        }
    }.build()
}

private fun List<PathNodes>.toPathBuilder(): (PathBuilder.() -> Unit) = {
    this@toPathBuilder.forEach { pathNode -> pathNode(pathNode) }
    close()
}

private fun PathBuilder.pathNode(node: PathNodes) {
    when (node) {
        is PathNodes.ArcTo -> if (node.isRelative) arcToRelative(node.a, node.b, node.theta, node.isMoreThanHalf, node.isPositiveArc, node.x, node.y) else arcTo(node.a, node.b, node.theta, node.isMoreThanHalf, node.isPositiveArc, node.x, node.y)
        is PathNodes.CurveTo -> if (node.isRelative) curveToRelative(node.x1, node.y1, node.x2, node.y2, node.x3, node.y3) else curveTo(node.x1, node.y1, node.x2, node.y2, node.x3, node.y3)
        is PathNodes.LineTo -> if (node.isRelative) lineToRelative(node.x, node.y) else lineTo(node.x, node.y)
        is PathNodes.MoveTo -> if (node.isRelative) moveToRelative(node.x, node.y) else moveTo(node.x, node.y)
        is PathNodes.HorizontalLineTo -> if (node.isRelative) horizontalLineToRelative(node.x) else horizontalLineTo(node.x)
        is PathNodes.VerticalLineTo -> if (node.isRelative) verticalLineToRelative(node.y) else verticalLineTo(node.y)
        is PathNodes.QuadTo -> if (node.isRelative) quadToRelative(node.x1, node.y1, node.x2, node.y2) else quadTo(node.x1, node.y1, node.x2, node.y2)
        is PathNodes.ReflectiveCurveTo -> if (node.isRelative) reflectiveCurveToRelative(node.x1, node.y1, node.x2, node.y2) else reflectiveCurveTo(node.x1, node.y1, node.x2, node.y2)
        is PathNodes.ReflectiveQuadTo -> if (node.isRelative) reflectiveQuadToRelative(node.x1, node.y1) else reflectiveQuadTo(node.x1, node.y1)
    }
}

private fun ComposeBrush.toBrush(): Brush {
    return when (this) {
        is ComposeBrush.SolidColor -> SolidColor(value = ComposeColor(value).toColor())
        is ComposeBrush.Gradient.Linear -> Brush.linearGradient()
        is ComposeBrush.Gradient.Radial -> Brush.radialGradient()
        is ComposeBrush.Gradient.Sweep -> Brush.sweepGradient()
    }
}

@OptIn(ExperimentalStdlibApi::class)
private fun ComposeColor.toColor(): Color = Color(color.hexToLong())