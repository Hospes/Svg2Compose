package app.s2c.ui.common.input

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface InputState<V> {
    val value: V
    val error: Error?

    interface Error
}

interface InputStateHelper<V, out S : InputState<V>> {
    val value: MutableStateFlow<V>
    val error: MutableStateFlow<out InputState.Error?>
    val state: Flow<S>
}