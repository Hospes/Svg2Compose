package app.s2c.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Provider
import kotlin.reflect.KClass

@ContributesBinding(ViewModelScope::class)
@Inject
class MetroViewModelFactory(
    /**
     * Map of simple ViewModel factories for ViewModels that have no assisted dependencies.
     */
    private val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>>,
    /**
     * Map of advanced ViewModel factories that require one or more assisted dependencies including [SavedStateHandle].
     */
    private val viewModelAssistedProviders: Map<KClass<out ViewModel>, Provider<ViewModelAssistedFactory>> = emptyMap(),
) : ViewModelProvider.Factory {

    companion object {
        /** Creation extra key for the callbacks that create @AssistedInject-annotated ViewModels.  */
        val CREATION_CALLBACK_KEY = object : CreationExtras.Key<CreationExtras.(Any) -> ViewModel> {}
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        return viewModelProviders[modelClass]?.invoke() as T?
            ?: run {
                // The callback is meant to use an existing factory and provide the remaining
                // assisted dependencies to complete the creation of the ViewModel.
                val callback = extras[CREATION_CALLBACK_KEY] ?: return@run null
                val viewModelFactory = viewModelAssistedProviders[modelClass] ?: return@run null
                with(extras) {
                    callback(viewModelFactory.invoke()) as? T
                }
            }
            ?: throw IllegalArgumentException("Unknown model class $modelClass")

//        return try {
//            provider() as T
//        } catch (e: Exception) {
//            throw RuntimeException(e)
//        }
    }
}