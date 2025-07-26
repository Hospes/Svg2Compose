package app.s2c

import android.app.Application
import app.s2c.di.AppGraph
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

@DependencyGraph(AppScope::class, isExtendable = true)
interface AndroidAppGraph : AppGraph {
    val application: Application

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(@Provides application: Application): AndroidAppGraph
    }
}