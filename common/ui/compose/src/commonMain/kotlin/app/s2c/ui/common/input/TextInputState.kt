package app.s2c.ui.common.input

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import app.s2c.ui.resources.Res
import app.s2c.ui.resources.common_error_invalid
import app.s2c.ui.resources.common_error_required
import org.jetbrains.compose.resources.stringResource

@Stable
interface TextInputState : InputState<TextInputState.Error> {
    @Immutable
    interface Error : InputState.Error {
        data object Required : Error {
            @Composable
            override fun asLabel(): String = stringResource(Res.string.common_error_required)
        }

        data object Invalid : Error {
            @Composable
            override fun asLabel(): String = stringResource(Res.string.common_error_invalid)
        }

        data class Custom(val message: String) : Error {
            @Composable
            override fun asLabel(): String = message
        }
    }

    companion object {
        val Preview = DefaultTextInputState(
            fieldState = TextFieldState(initialText = "Some text"),
            errorState = mutableStateOf(null)
        )
    }
}

@Stable
interface TextInputStateHelper : InputStateHelper<TextInputState, TextInputState.Error> {
    fun validate(onValidate: ((String) -> TextInputState.Error?)? = null): TextInputState.Error?
    suspend fun validateSuspend(onValidate: (suspend (String) -> TextInputState.Error?)? = null): TextInputState.Error?

    companion object {
        val Required: (String) -> TextInputState.Error? = {
            when {
                it.isBlank() -> TextInputState.Error.Required
                else -> null
            }
        }
    }
}

@Stable
data class DefaultTextInputState(
    override val fieldState: TextFieldState,
    override val errorState: MutableState<TextInputState.Error?> = mutableStateOf(null),
    override val enabledState: MutableState<Boolean> = mutableStateOf(true),
) : TextInputState {

    constructor(
        initialText: String = "",
        initialError: TextInputState.Error? = null,
        initialEnabled: Boolean = true,
    ) : this(
        fieldState = TextFieldState(initialText = initialText),
        errorState = mutableStateOf(initialError),
        enabledState = mutableStateOf(initialEnabled),
    )

    companion object {
        /**
         * A Saver for [DefaultTextInputState] that handles process death and configuration changes.
         * Note: Validation error state is not saved/restored as it should be re-validated.
         */
        fun Saver(): Saver<DefaultTextInputState, Any> = Saver(
            save = {
                with(TextFieldState.Saver) { save(it.fieldState) }
            },
            restore = {
                DefaultTextInputState(
                    fieldState = TextFieldState.Saver.restore(it)!!,
                    errorState = mutableStateOf(null)
                )
            }
        )
    }
}

@Stable
class DefaultTextInputStateHelper(
    initialText: String = "",
    private val onValidate: (String) -> TextInputState.Error? = { null }
) : TextInputStateHelper {
    /**
     * The UI-facing state object. Pass this to your `ViewState` data class.
     */
    override val state: TextInputState = DefaultTextInputState(initialText = initialText)

    /**
     * Runs the validation logic against the current text and updates the error state.
     *
     * @return `true` if the input is valid, `false` otherwise.
     */
    override fun validate(onValidate: ((String) -> TextInputState.Error?)?): TextInputState.Error? = when (onValidate) {
        null -> onValidate(text)
        else -> onValidate(text)
    }.also(::setError)

    override suspend fun validateSuspend(onValidate: (suspend (String) -> TextInputState.Error?)?): TextInputState.Error? = when (onValidate) {
        null -> onValidate(text)
        else -> onValidate(text)
    }.also(::setError)
}