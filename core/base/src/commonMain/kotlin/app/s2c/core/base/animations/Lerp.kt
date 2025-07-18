package app.s2c.core.base.animations

/**
 * Performs linear interpolation between two float values.
 *
 * Calculates the intermediate value between startValue and endValue based on the given fraction.
 * When fraction is 0.0, returns startValue. When fraction is 1.0, returns endValue.
 *
 * @param startValue The starting value for interpolation
 * @param endValue The ending value for interpolation
 * @param fraction The interpolation factor between 0.0 and 1.0
 * @return The interpolated value between startValue and endValue
 */
fun lerp(
    startValue: Float,
    endValue: Float,
    fraction: Float,
) = startValue + fraction * (endValue - startValue)
