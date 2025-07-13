package app.s2c.ui.config

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.s2c.ui.common.input.TextInputState
import app.s2c.ui.common.theme.AppTheme
import app.s2c.ui.common.ui.AppDialog
import com.teobaranga.kotlin.inject.viewmodel.runtime.compose.injectedViewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
data object ConfigDialog

@Composable
internal fun ConfigDialog(
    navigateUp: () -> Unit,
) {
    ConfigDialog(
        viewModel = injectedViewModel(),
        navigateUp = navigateUp,
    )
}

@Composable
private fun ConfigDialog(
    viewModel: ConfigViewModel,
    navigateUp: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ConfigDialog(
        state = state,
        navigateUp = navigateUp,
        sourceCodeInputState = viewModel.sourceCodeInputState,
        outputCodeInputState = viewModel.outputCodeInputState,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ConfigDialog(
    state: ConfigViewState,
    navigateUp: () -> Unit,
    sourceCodeInputState: TextInputState,
    outputCodeInputState: TextInputState,
) {
    AppDialog(
        title = { Text(text = "Config") },
    ) {
        Text(text = "body of config dialog")
    }
}


@Preview
@Composable
private fun Preview() {
    AppTheme {
        ConfigDialog(
            state = ConfigViewState.Init,
            navigateUp = {},
            sourceCodeInputState = TextInputState.Preview,
            outputCodeInputState = TextInputState.Preview,
        )
    }
}