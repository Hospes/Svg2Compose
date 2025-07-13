package app.s2c.ui.config

import androidx.compose.runtime.Immutable

@Immutable
data class ConfigViewState(
    val isLoading: Boolean = false,
) {
    companion object {
        val Init = ConfigViewState()
    }
}