package app.s2c.data.domain.avg.gradient

import app.s2c.data.logger.warn

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
                warn("'$value' is an unsupported type of type for ${AvgGradient.TAG_NAME} tag. Using default to '$Linear")
                Linear
            }
        }
    }
}
