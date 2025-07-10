package app.s2c.ui.converter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.s2c.ui.common.theme.AppTheme
import app.s2c.ui.resources.Res
import app.s2c.ui.resources.rail_screen_balance
import com.teobaranga.kotlin.inject.viewmodel.runtime.compose.injectedViewModel
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Serializable
data object ConverterScreen

@Composable
internal fun ConverterScreen() {
    ConverterScreen(
        viewModel = injectedViewModel(),
    )
}

@Composable
private fun ConverterScreen(
    viewModel: ConverterViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ConverterScreen(
        state = state,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun ConverterScreen(
    state: ConverterViewState,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(Res.string.rail_screen_balance)) },
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddings ->
        Column(
            modifier = Modifier.padding(paddings),
        ) {
        }
    }
}


@Preview
@Composable
private fun Preview() {
    AppTheme {
        ConverterScreen(
            state = ConverterViewState.Init,
        )
    }
}