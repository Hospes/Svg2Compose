package app.s2c.ui.di

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provider
import kotlin.reflect.KClass

@DependencyGraph(ViewModelScope::class)
interface ViewModelGraph {
    @Multibinds(allowEmpty = true)
    val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>>
}