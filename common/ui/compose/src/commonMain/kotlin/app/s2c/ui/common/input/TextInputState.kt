package app.s2c.ui.common.input

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update

interface TextInputState : InputState<TextFieldValue> {
    val isValidating: Boolean
    val enabled: Boolean
    override val error: Error?

    interface Error : InputState.Error {
        data object Required : Error
        data object Invalid : Error
    }
}

interface TextInputStateHelper<out S : TextInputState> : InputStateHelper<TextFieldValue, S> {
    val isValidating: MutableStateFlow<Boolean>
    val enabled: MutableStateFlow<Boolean>

    fun setValue(s: String) = with(value) { update { it.copy(text = s, selection = TextRange(s.length)) } }
    fun setValue(s: TextFieldValue) = with(value) { value = s }
}


data class DefaultTextInputState(
    override val value: TextFieldValue = TextFieldValue(),
    override val isValidating: Boolean = false,
    override val enabled: Boolean = true,
    override val error: TextInputState.Error? = null
) : TextInputState {

    constructor(
        value: String,
        isValidating: Boolean = false,
        enabled: Boolean = true,
        error: TextInputState.Error? = null,
    ) : this(
        value = TextFieldValue(value, selection = TextRange(value.length)),
        isValidating = isValidating,
        enabled = enabled,
        error = error,
    )

    companion object {
        val Init = DefaultTextInputState()
    }
}

class DefaultTextInputStateHelper(
    override val value: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue()),
    override val isValidating: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val enabled: MutableStateFlow<Boolean> = MutableStateFlow(true),
    override val error: MutableStateFlow<TextInputState.Error?> = MutableStateFlow(null),
) : TextInputStateHelper<DefaultTextInputState> {

    constructor(
        initValue: String,
        initIsValidating: Boolean = false,
        initEnabled: Boolean = true,
        initError: TextInputState.Error? = null,
    ) : this(
        value = MutableStateFlow(TextFieldValue(initValue, selection = TextRange(initValue.length))),
        isValidating = MutableStateFlow(initIsValidating),
        enabled = MutableStateFlow(initEnabled),
        error = MutableStateFlow(initError),
    )

    constructor(
        initValue: TextFieldValue,
        initIsValidating: Boolean = false,
        initEnabled: Boolean = true,
        initError: TextInputState.Error? = null,
    ) : this(
        value = MutableStateFlow(initValue),
        isValidating = MutableStateFlow(initIsValidating),
        enabled = MutableStateFlow(initEnabled),
        error = MutableStateFlow(initError),
    )


    override val state: Flow<DefaultTextInputState> = combine(
        value, isValidating, enabled, error
    ) { value, validating, enabled, error ->
        DefaultTextInputState(
            value = value,
            isValidating = validating,
            enabled = enabled,
            error = error,
        )
    }

    override fun setValue(s: String) {
        super.setValue(s)
        error.value = null
    }

    override fun setValue(s: TextFieldValue) {
        super.setValue(s)
        error.value = null
    }
}