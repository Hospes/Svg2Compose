package app.s2c.ui.common.input

import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import app.s2c.core.base.extensions.combine
import app.s2c.ui.common.input.SavableTextInputState.Status

interface SavableTextInputState : TextInputState {
    val needToSave: Boolean
    val status: Status
    override val error: Error?

    enum class Status { IDLE, SAVING, SAVED }

    interface Error : TextInputState.Error {
        data object Required : Error
        data object Invalid : Error
        data object FailedToSave : Error
    }
}

interface SavableTextInputStateHelper<out S : SavableTextInputState> : TextInputStateHelper<S> {
    val needToSave: MutableStateFlow<Boolean>
    val status: MutableStateFlow<Status>
}


data class DefaultSavableTextInputState(
    override val value: TextFieldValue = TextFieldValue(),
    override val isValidating: Boolean = false,
    override val enabled: Boolean = true,
    override val needToSave: Boolean = false,
    override val status: Status = Status.IDLE,
    override val error: SavableTextInputState.Error? = null
) : SavableTextInputState {

    constructor(
        value: String,
        isValidating: Boolean = false,
        enabled: Boolean = true,
        needToSave: Boolean = false,
        status: Status = Status.IDLE,
        error: SavableTextInputState.Error? = null,
    ) : this(
        value = TextFieldValue(value),
        isValidating = isValidating,
        enabled = enabled,
        needToSave = needToSave,
        status = status,
        error = error,
    )

    companion object {
        val Init = DefaultSavableTextInputState()
    }
}


class DefaultSavableTextInputStateHelper(
    override val value: MutableStateFlow<TextFieldValue> = MutableStateFlow(TextFieldValue()),
    override val isValidating: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val enabled: MutableStateFlow<Boolean> = MutableStateFlow(true),
    override val needToSave: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val status: MutableStateFlow<Status> = MutableStateFlow(Status.IDLE),
    override val error: MutableStateFlow<SavableTextInputState.Error?> = MutableStateFlow(null),
) : SavableTextInputStateHelper<DefaultSavableTextInputState> {

    constructor(
        initValue: String,
        initIsValidating: Boolean = false,
        initEnabled: Boolean = true,
        initNeedToSave: Boolean = false,
        initStatus: Status = Status.IDLE,
        initError: SavableTextInputState.Error? = null,
    ) : this(
        value = MutableStateFlow(TextFieldValue(initValue)),
        isValidating = MutableStateFlow(initIsValidating),
        enabled = MutableStateFlow(initEnabled),
        needToSave = MutableStateFlow(initNeedToSave),
        status = MutableStateFlow(initStatus),
        error = MutableStateFlow(initError),
    )

    constructor(
        initValue: TextFieldValue,
        initIsValidating: Boolean = false,
        initEnabled: Boolean = true,
        initNeedToSave: Boolean = false,
        initStatus: Status = Status.IDLE,
        initError: SavableTextInputState.Error? = null,
    ) : this(
        value = MutableStateFlow(initValue),
        isValidating = MutableStateFlow(initIsValidating),
        enabled = MutableStateFlow(initEnabled),
        needToSave = MutableStateFlow(initNeedToSave),
        status = MutableStateFlow(initStatus),
        error = MutableStateFlow(initError),
    )


    override val state: Flow<DefaultSavableTextInputState> = combine(
        value, isValidating, enabled, needToSave, status, error
    ) { value, validating, enabled, needToSave, status, error ->
        DefaultSavableTextInputState(
            value = value,
            isValidating = validating,
            enabled = enabled,
            needToSave = needToSave,
            status = status,
            error = error,
        )
    }
}