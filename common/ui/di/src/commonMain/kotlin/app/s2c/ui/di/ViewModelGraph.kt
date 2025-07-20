package app.s2c.ui.di

import androidx.lifecycle.ViewModelProvider
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesGraphExtension

@ContributesGraphExtension(ViewModelScope::class)
interface ViewModelGraph {

    val vmFactory: ViewModelProvider.Factory


    @ContributesGraphExtension.Factory(AppScope::class)
    fun interface Factory {
        fun createViewModelGraph(): ViewModelGraph
    }
}