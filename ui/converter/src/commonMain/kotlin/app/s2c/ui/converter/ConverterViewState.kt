package app.s2c.ui.converter

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class ConverterViewState(
    val output: Output = Output.Placeholder,
) {

    @Immutable
    sealed interface Output {
        data class Preview(val icon: ImageVector) : Output
        data class Code(val code: String) : Output
        data class Error(val message: String) : Output
        data object Placeholder : Output
    }

    companion object {
        val Init = ConverterViewState()
    }
}