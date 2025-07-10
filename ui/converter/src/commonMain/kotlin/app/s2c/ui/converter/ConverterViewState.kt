package app.s2c.ui.converter

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class ConverterViewState(
    val output: Output? = null,
) {

    @Immutable
    data class Output(val preview: ImageVector? = null)

    companion object {
        val Init = ConverterViewState()
    }
}