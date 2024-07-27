package app.s2c.data.geom.transform

import app.s2c.data.domain.PathCommand
import app.s2c.data.domain.PathNodes
import app.s2c.data.geom.AffineTransformation

internal data object HorizontalLineTransformation : PathTransformation<PathNodes.HorizontalLineTo>() {
    override fun PathNodes.HorizontalLineTo.applyTransformation(
        cursor: FloatArray,
        start: FloatArray,
        transformation: AffineTransformation,
    ): PathNodes {
        // convert to lineTo to handle two-dimensional transforms
        return if (isRelative) {
            new(
                args = listOf(
                    x,
                    0f,
                ),
                command = PathCommand.LineTo,
            )
        } else {
            new(
                args = listOf(
                    x,
                    cursor[1],
                ),
                command = PathCommand.LineTo,
            )
        }
    }
}
