package app.s2c.di

import androidx.lifecycle.ViewModelProvider
import app.s2c.core.base.util.AppCoroutineDispatchers
import app.s2c.ui.di.ViewModelGraph
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

interface AppGraph : ViewModelGraph.Factory {

    val initializers: AppInitializers

    val viewModelFactory: ViewModelProvider.Factory


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