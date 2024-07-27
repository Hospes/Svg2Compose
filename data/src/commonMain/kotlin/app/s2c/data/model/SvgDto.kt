package app.s2c.data.model

data class SvgDto(
    val width: Float = DEFAULT_SIZE,
    val height: Float = DEFAULT_SIZE,
    val viewportWidth: Float = DEFAULT_VIEWPORT_SIZE,
    val viewportHeight: Float = DEFAULT_VIEWPORT_SIZE,
    val paths: List<Path> = emptyList(),
) {
    data class Path(
        val source: String,
        val segments: List<Pair<Char, List<Float>>>,
        val fillType: String? = null,
        val fillColor: String? = null,
        val fillAlpha: Float? = null,
        val strokeColor: String? = null,
        val strokeAlpha: Float? = null,
        val strokeLineWidth: Float? = null,
        val strokeLineCap: String? = null,
        val strokeLineJoin: String? = null,
        val strokeLineMiter: Float? = null,
    )

    companion object {
        const val DEFAULT_SIZE = 24F
        const val DEFAULT_VIEWPORT_SIZE = 24F
    }
}
