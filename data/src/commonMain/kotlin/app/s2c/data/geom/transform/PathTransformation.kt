package app.s2c.data.geom.transform

import app.s2c.data.domain.PathCommand
import app.s2c.data.domain.PathNodes
import app.s2c.data.domain.builder.pathNode
import app.s2c.data.geom.AffineTransformation
import app.s2c.data.geom.Point2D

// TODO(https://github.com/rafaeltonholo/svg-to-compose/issues/44): migrate from FloatArray to DoubleArray
internal sealed class PathTransformation<T : PathNodes> {
    abstract fun T.applyTransformation(
        cursor: FloatArray,
        start: FloatArray = floatArrayOf(),
        transformation: AffineTransformation = AffineTransformation.Identity,
    ): PathNodes

    protected fun transformAbsolutePoint(matrix: Array<out FloatArray>, x: Float, y: Float): Point2D {
        val newX = matrix[0][0] * x + matrix[0][1] * y + matrix[0][2]
        val newY = matrix[1][0] * x + matrix[1][1] * y + matrix[1][2]
        return Point2D(newX, newY)
    }

    protected fun transformRelativePoint(matrix: Array<out FloatArray>, x: Float, y: Float): Point2D {
        val newX = matrix[0][0] * x + matrix[0][1] * y
        val newY = matrix[1][0] * x + matrix[1][1] * y
        return Point2D(newX, newY)
    }

    fun PathNodes.new(
        args: List<Any>,
        command: PathCommand? = null,
    ): PathNodes = pathNode(command ?: this.command) {
        args(args)
        isRelative = this@new.isRelative
        minified = this@new.minified
        close = this@new.shouldClose
    }
}
