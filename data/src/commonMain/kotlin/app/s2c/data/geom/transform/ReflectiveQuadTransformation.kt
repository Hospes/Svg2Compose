package app.s2c.data.geom.transform

import app.s2c.data.model.PathNodes
import app.s2c.data.geom.AffineTransformation

internal data object ReflectiveQuadTransformation : PathTransformation<PathNodes.ReflectiveQuadTo>() {
    override fun PathNodes.ReflectiveQuadTo.applyTransformation(
        cursor: FloatArray,
        start: FloatArray,
        transformation: AffineTransformation,
    ): PathNodes {
        val (x1, y1) = if (isRelative) {
            cursor[0] += x1
            cursor[1] += y1
            start[0] = cursor[0]
            start[1] = cursor[1]
            transformRelativePoint(transformation.matrix, x1, y1)
        } else {
            cursor[0] = x1
            cursor[1] = y1
            start[0] = cursor[0]
            start[1] = cursor[1]
            transformAbsolutePoint(transformation.matrix, x1, y1)
        }
        return new(listOf(x1, y1))
    }
}
