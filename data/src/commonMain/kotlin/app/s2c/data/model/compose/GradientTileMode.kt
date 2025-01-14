package app.s2c.data.model.compose

import app.s2c.data.parser.method.MethodSizeAccountable

@JvmInline
value class GradientTileMode private constructor(override val value: String) :
    ComposeType<String>, MethodSizeAccountable {
    companion object {
        private const val NAME = "TileMode"
        private val IMPORT = setOf("androidx.compose.ui.graphics.$NAME")

        // Each GradientTileMode contributes approximately 4 bytes.
        private const val BYTECODE_SIZE = 5
        val Clamp = GradientTileMode("Clamp")
        val Repeated = GradientTileMode("Repeated")
        val Mirror = GradientTileMode("Mirror")
        val Decal = GradientTileMode("Decal")

        operator fun invoke(value: String?): GradientTileMode? = value?.let {
            val tileMode = GradientTileMode(value.replaceFirstChar { it.uppercaseChar() })
            return tileMode.toCompose()?.let { tileMode }
        }
    }

    override val name: String
        get() = NAME
    override val imports: Set<String>
        get() = IMPORT

    // Each TileMode accounts with 5 bytes, approximately.
    override val approximateByteSize: Int get() = BYTECODE_SIZE

    override fun toCompose(): String? = when (this.value.lowercase()) {
        Clamp.value.lowercase() -> "$name.$Clamp"
        Repeated.value.lowercase(), "repeat" -> "$name.$Repeated"
        Mirror.value.lowercase() -> "$name.$Mirror"
        Decal.value.lowercase() -> "$name.$Decal"

        else -> null
    }

    override fun toString(): String = value
}
