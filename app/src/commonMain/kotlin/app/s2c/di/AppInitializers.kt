package app.s2c.di

import app.s2c.core.base.appinitializers.AppInitializer
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.SingleIn

@Inject
@SingleIn(AppScope::class)
class AppInitializers(
    private val initializers: Lazy<Set<AppInitializer>>,
) : AppInitializer {
    override fun initialize() {
        for (initializer in initializers.value) {
            initializer.initialize()
        }
    }
}
