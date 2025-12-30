package app.s2c.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlin.reflect.KClass

@ContributesBinding(AppScope::class)
@Inject
class MetroViewModelFactory(
    val graphFactory: ViewModelGraph.Factory,
) : ViewModelProvider.Factory {

    companion object {
        /** Creation extra key for the callbacks that create @AssistedInject-annotated ViewModels.  */
        val CREATION_CALLBACK_KEY = object : CreationExtras.Key<CreationExtras.(Any) -> ViewModel> {}
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        val viewModelGraph = graphFactory.createViewModelGraph(extras)

        return viewModelGraph.viewModelProviders[modelClass]?.invoke() as T?
            ?: run {
                // The callback is meant to use an existing factory and provide the remaining
                // assisted dependencies to complete the creation of the ViewModel.
                val callback = extras[CREATION_CALLBACK_KEY] ?: return@run null
                val viewModelFactory = viewModelGraph.viewModelAssistedProviders[modelClass] ?: return@run null
                with(extras) {
                    callback(viewModelFactory.invoke()) as? T
                }
            }
            ?: throw IllegalArgumentException("Unknown model class $modelClass")
    }
}