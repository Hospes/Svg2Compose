package app.s2c.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.Provider
import kotlin.reflect.KClass

@ContributesBinding(AppScope::class)
@Inject
class MetroViewModelFactory(
    private val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    companion object {
        /** Creation extra key for the callbacks that create @AssistedInject-annotated ViewModels.  */
        val CREATION_CALLBACK_KEY = object : CreationExtras.Key<CreationExtras.(Any) -> ViewModel> {}
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras
    ): T {
        val provider =
            viewModelProviders[modelClass]
                ?: throw IllegalArgumentException("Unknown model class $modelClass")

        return try {
            provider() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}