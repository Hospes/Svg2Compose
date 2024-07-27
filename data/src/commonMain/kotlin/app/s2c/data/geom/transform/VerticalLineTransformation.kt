package app.s2c.data.geom.transform

import app.s2c.data.model.PathCommand
import app.s2c.data.model.PathNodes
import app.s2c.data.geom.AffineTransformation

internal data object VerticalLineTransformation : PathTransformation<PathNodes.VerticalLineTo>() {
    override fun PathNodes.VerticalLineTo.applyTransformation(
        cursor: FloatArray,
        start: FloatArray,
        transformation: AffineTransformation,
    ): PathNodes {
        // convert to lineTo to handle two-dimensional transforms
        return if (isRelative) {
            new(
                args = listOf(
                    0f,
                    y,
                ),
                command = PathCommand.LineTo,
            )
        } else {
            new(
                args = listOf(
                    cursor[0],
                    y,
                ),
                command = PathCommand.LineTo,
            )
        }
    }
}
