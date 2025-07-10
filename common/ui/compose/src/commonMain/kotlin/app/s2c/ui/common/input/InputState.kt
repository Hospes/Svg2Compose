package app.s2c.ui.common.input

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

@Stable
interface InputState<E : InputState.Error> {
    val fieldState: TextFieldState
    val errorState: MutableState<E?>
    val enabledState: MutableState<Boolean>

    val isError: Boolean
        get() = errorState.value != null

    @Immutable
    interface Error {
        @Composable
        fun asLabel(): String
    }
}

@Stable
interface InputStateHelper<S : InputState<E>, E : InputState.Error> {
    val state: S

    val text: String
        get() = state.fieldState.text.toString()
    val textFlow: Flow<String>
        get() = snapshotFlow { state.fieldState.text.toString() }

    val error: E?
        get() = state.errorState.value
    val errorFlow: Flow<E?>
        get() = snapshotFlow { state.errorState.value }


    fun setText(text: String) = with(state) { fieldState.setTextAndPlaceCursorAtEnd(text); errorState.value = null }

    fun setError(error: E?) = with(state.errorState) { value = error }
    fun clearError() = setError(null)

    fun setEnabled(enabled: Boolean) = with(state.enabledState) { value = enabled }

    fun clear() = with(state) { fieldState.clearText(); clearError() }

    suspend fun clearErrorOnInputUpdate() {
        snapshotFlow { state.fieldState.text }
            .distinctUntilChanged()
            .collectLatest { state.errorState.value = null }
    }
}