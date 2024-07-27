package app.s2c.data.domain.svg.gradient

import app.s2c.data.domain.compose.GradientTileMode
import app.s2c.data.logger.warn

enum class SvgGradientSpreadMethod {
    Pad,
    Reflect,
    Repeat,
    ;

    override fun toString(): String = name.lowercase()

    fun toCompose(): GradientTileMode = when (this) {
        Pad -> GradientTileMode.Clamp
        Reflect -> GradientTileMode.Mirror
        Repeat -> GradientTileMode.Repeated
    }

    companion object {
        operator fun invoke(value: String): SvgGradientSpreadMethod = when (value.lowercase()) {
            Pad.toString() -> Pad
            Reflect.toString() -> Reflect
            Repeat.toString() -> Repeat
            else -> {
                warn(
                    "'$value' is an unsupported type of spreadMethod. Using default to '$Pad'",
                )
                Pad
            }
        }
    }
}
