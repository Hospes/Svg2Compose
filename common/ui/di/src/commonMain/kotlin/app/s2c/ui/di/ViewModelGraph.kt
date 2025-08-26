package app.s2c.ui.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.GraphExtension
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.Provides
import kotlin.reflect.KClass

@GraphExtension(ViewModelScope::class)
interface ViewModelGraph {

    /**
     * Map of simple ViewModel factories for ViewModels that have no assisted dependencies.
     */
    @Multibinds(allowEmpty = true)
    val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>>

    /**
     * Map of advanced ViewModel factories that require one or more assisted dependencies.
     */
    @Multibinds(allowEmpty = true)
    val viewModelAssistedProviders: Map<KClass<out ViewModel>, Provider<ViewModelAssistedFactory>>


    @Provides
    fun provideSavedStateHandle(creationExtras: CreationExtras): SavedStateHandle = creationExtras.createSavedStateHandle()


    @GraphExtension.Factory
    fun interface Factory {
        fun createViewModelGraph(@Provides creationExtras: CreationExtras): ViewModelGraph
    }
}