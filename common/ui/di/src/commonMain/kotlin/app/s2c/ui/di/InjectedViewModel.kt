package app.s2c.ui.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel

val LocalViewModelFactoryOwner = staticCompositionLocalOf<ViewModelFactoryOwner> {
    error("No ViewModelFactoryOwner was provided provided via LocalViewModelFactoryOwner")
}

/**
 * Returns or creates a [ViewModel] annotated with [ContributesViewModel], scoped to the local
 * [ViewModelStoreOwner]. This can be a navigation backstack entry, a fragment, or an activity.
 *
 * Requires a [LocalViewModelFactoryOwner] to be set in the composition tree, one that can provide a factory
 * such as [KotlinInjectViewModelFactory].
 *
 * Can only handle ViewModels without assisted dependencies.
 *
 * @see [viewModel]
 */
@Composable
inline fun <reified VM : ViewModel> injectedViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
    factory: ViewModelProvider.Factory? = LocalViewModelFactoryOwner.current.viewModelFactory,
): VM {
    return viewModel<VM>(
        viewModelStoreOwner = viewModelStoreOwner,
        key = key,
        factory = factory,
    )
}