package app.s2c.ui.converter

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import app.s2c.data.parser.IconParser

@Immutable
data class ConverterViewState(
    val input: Input = Input.Init,
    val output: Output = Output.Init,
) {
    @Immutable
    data class Input(
        val parser: Parser = Parser.SVG,
    ) {
        enum class Parser(val p: IconParser) { SVG(IconParser.SvgParser), VECTOR(IconParser.AndroidVectorParser) }

        companion object {
            val Init = Input()
        }
    }

    @Immutable
    sealed interface Output {
        data class Preview(val icon: ImageVector) : Output
        data class Code(val code: String) : Output
        data class Error(val message: String) : Output
        data object Placeholder : Output

        companion object {
            val Init = Output.Placeholder
        }
    }

    companion object {
        val Init = ConverterViewState()
    }
}