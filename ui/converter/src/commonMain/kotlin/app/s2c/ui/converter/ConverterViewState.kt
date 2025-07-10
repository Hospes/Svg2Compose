package app.s2c.ui.converter

import androidx.compose.runtime.Immutable

@Immutable
data class ConverterViewState(
    val isLoading: Boolean = false,
) {
    companion object {
        val Init = ConverterViewState()
    }
}