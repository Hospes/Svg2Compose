package app.s2c.data.geom.transform

import app.s2c.data.model.PathNodes
import app.s2c.data.geom.AffineTransformation

internal data object LineTransformation : PathTransformation<PathNodes.LineTo>() {
    override fun PathNodes.LineTo.applyTransformation(
        cursor: FloatArray,
        start: FloatArray,
        transformation: AffineTransformation,
    ): PathNodes {
        val (x, y) = if (isRelative) {
            cursor[0] += x
            cursor[1] += y
            start[0] = cursor[0]
            start[1] = cursor[1]
            transformRelativePoint(transformation.matrix, x, y)
        } else {
            cursor[0] = x
            cursor[1] = y
            start[0] = cursor[0]
            start[1] = cursor[1]
            transformAbsolutePoint(transformation.matrix, x, y)
        }
        return new(listOf(x, y))
    }
}
