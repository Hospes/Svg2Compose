package app.s2c.ui.config

import androidx.compose.runtime.Immutable

@Immutable
data class ConfigViewState(
    val optimize: Boolean = false,
    val addToMaterial: Boolean = false,
    val noPreview: Boolean = false,
    val makeInternal: Boolean = false,
    val minified: Boolean = false,
) {
    companion object {
        val Init = ConfigViewState()
    }
}