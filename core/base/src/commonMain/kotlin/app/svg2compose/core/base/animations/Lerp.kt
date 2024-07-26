package app.svg2compose.core.base.animations

fun lerp(
  startValue: Float,
  endValue: Float,
  fraction: Float,
) = startValue + fraction * (endValue - startValue)
