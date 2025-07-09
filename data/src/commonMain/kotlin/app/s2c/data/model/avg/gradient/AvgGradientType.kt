package app.s2c.data.model.avg.gradient

import app.s2c.core.logs.Log

enum class AvgGradientType {
    Linear,
    Radial,
    Sweep,
    ;

    override fun toString(): String = name.lowercase()

    companion object {
        operator fun invoke(value: String): AvgGradientType = when (value) {
            Linear.toString() -> Linear
            Radial.toString() -> Radial
            Sweep.toString() -> Sweep
            else -> {
                Log.warn("'$value' is an unsupported type of type for ${AvgGradient.TAG_NAME} tag. Using default to '$Linear")
                Linear
            }
        }
    }
}
