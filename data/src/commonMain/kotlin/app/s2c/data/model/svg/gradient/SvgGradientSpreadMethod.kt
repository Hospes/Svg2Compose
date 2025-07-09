package app.s2c.data.model.svg.gradient

import app.s2c.core.logs.Log
import app.s2c.data.model.compose.GradientTileMode

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
                Log.warn(
                    "'$value' is an unsupported type of spreadMethod. Using default to '$Pad'",
                )
                Pad
            }
        }
    }
}
