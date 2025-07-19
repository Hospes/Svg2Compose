package app.s2c.di

import androidx.lifecycle.ViewModelProvider
import app.s2c.core.base.util.AppCoroutineDispatchers
import dev.zacsweers.metro.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@DependencyGraph(AppScope::class)
interface AppGraph {

    val vmFactory: ViewModelProvider.Factory

    val initializers: AppInitializers


    val dispatchers: AppCoroutineDispatchers

    @SingleIn(AppScope::class)
    @Provides
    fun provideCoroutineDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        databaseWrite = Dispatchers.IO.limitedParallelism(1),
        databaseRead = Dispatchers.IO.limitedParallelism(4),
        computation = Dispatchers.Default,
        main = Dispatchers.Main,
    )


    val appScope: CoroutineScope

    @SingleIn(AppScope::class)
    @Provides
    fun provideApplicationCoroutineScope(
        dispatchers: AppCoroutineDispatchers,
    ): CoroutineScope = CoroutineScope(dispatchers.main + SupervisorJob())
}