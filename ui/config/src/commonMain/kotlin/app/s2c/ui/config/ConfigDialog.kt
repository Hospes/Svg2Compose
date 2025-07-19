package app.s2c.ui.config

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.s2c.ui.common.input.TextInputState
import app.s2c.ui.common.theme.AppTheme
import app.s2c.ui.common.ui.AppColumnDialog
import app.s2c.ui.common.ui.AppSwitch
import app.s2c.ui.common.ui.AppTextField
import app.s2c.ui.di.injectedViewModel
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
        packageInputState = viewModel.packageInputState,
        receiverTypeInputState = viewModel.receiverTypeInputState,
        onOptimizeChanged = viewModel::onOptimizeChanged,
        onAddToMaterialChanged = viewModel::onAddToMaterialChanged,
        onNoPreviewChanged = viewModel::onNoPreviewChanged,
        onMakeInternalChanged = viewModel::onMakeInternalChanged,
        onMinifiedChanged = viewModel::onMinifiedChanged,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ConfigDialog(
    state: ConfigViewState,
    navigateUp: () -> Unit,
    packageInputState: TextInputState,
    receiverTypeInputState: TextInputState,
    onOptimizeChanged: (Boolean) -> Unit = {},
    onAddToMaterialChanged: (Boolean) -> Unit = {},
    onNoPreviewChanged: (Boolean) -> Unit = {},
    onMakeInternalChanged: (Boolean) -> Unit = {},
    onMinifiedChanged: (Boolean) -> Unit = {},
) {
    AppColumnDialog(
        title = {
            Text(text = "Config", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))
        },
        onClose = navigateUp,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.widthIn(min = 240.dp, max = 360.dp),
    ) {
        AppTextField(
            state = packageInputState,
            label = { Text("Package Name") },
            modifier = Modifier.fillMaxWidth(),
        )

        AppTextField(
            state = receiverTypeInputState,
            label = { Text("Receiver Type") },
            modifier = Modifier.fillMaxWidth(),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AppSwitch(
                checked = state.optimize,
                onCheckedChange = onOptimizeChanged,
                modifier = Modifier.fillMaxWidth(),
            ) { Text("Optimize") }

            AppSwitch(
                checked = state.addToMaterial,
                onCheckedChange = onAddToMaterialChanged,
                modifier = Modifier.fillMaxWidth(),
            ) { Text("Add to Material") }

            AppSwitch(
                checked = state.noPreview,
                onCheckedChange = onNoPreviewChanged,
                modifier = Modifier.fillMaxWidth(),
            ) { Text("No Preview") }

            AppSwitch(
                checked = state.makeInternal,
                onCheckedChange = onMakeInternalChanged,
                modifier = Modifier.fillMaxWidth(),
            ) { Text("Make Internal") }

            AppSwitch(
                checked = state.minified,
                onCheckedChange = onMinifiedChanged,
                modifier = Modifier.fillMaxWidth(),
            ) { Text("Minified") }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    AppTheme {
        ConfigDialog(
            state = ConfigViewState.Init,
            navigateUp = {},
            packageInputState = TextInputState.Preview,
            receiverTypeInputState = TextInputState.Preview,
            onOptimizeChanged = {},
            onAddToMaterialChanged = {},
            onNoPreviewChanged = {},
            onMakeInternalChanged = {},
            onMinifiedChanged = {},
        )
    }
}