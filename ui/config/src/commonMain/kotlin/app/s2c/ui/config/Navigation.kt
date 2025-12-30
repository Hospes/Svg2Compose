package app.s2c.ui.config

import androidx.compose.ui.window.DialogProperties
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.DialogSceneStrategy
import app.s2c.ui.di.injectedViewModel
import app.s2c.ui.navigation.Navigator

fun EntryProviderScope<NavKey>.addConfigDialog(
    navigator: Navigator,
) {
    entry<ConfigDialog>(
        metadata = DialogSceneStrategy.dialog(DialogProperties())
    ) { key ->
        ConfigDialog(
            viewModel = injectedViewModel<ConfigViewModel, ConfigViewModel.Factory> { it.create(key.id) },
            navigateUp = navigator::navigateUp,
        )
    }
}