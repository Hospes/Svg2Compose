package app.s2c.ui.converter

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import app.s2c.ui.di.injectedViewModel
import app.s2c.ui.navigation.Navigator

fun EntryProviderScope<NavKey>.addConverterScreen(
    navigator: Navigator,
    navigateConfig: () -> Unit,
) {
    entry<ConverterScreen> {
        ConverterScreen(
            viewModel = injectedViewModel(),
            navigateConfig = navigateConfig,
        )
    }
}