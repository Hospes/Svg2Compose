package app.s2c.ui.di

import androidx.lifecycle.ViewModelProvider
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.GraphExtension

@GraphExtension(ViewModelScope::class)
interface ViewModelGraph {

    val vmFactory: ViewModelProvider.Factory


    @ContributesTo(AppScope::class)
    @GraphExtension.Factory
    fun interface Factory {
        fun createViewModelGraph(): ViewModelGraph
    }
}