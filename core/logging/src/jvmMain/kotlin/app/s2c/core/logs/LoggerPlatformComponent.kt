package app.s2c.core.logs

import app.s2c.core.base.appinitializers.AppInitializer
import dev.zacsweers.metro.IntoSet
import dev.zacsweers.metro.Provides

actual interface LoggerPlatformComponent {
    @Provides
    @IntoSet
    fun provideLogbackInitializer(impl: LogbackInitializer): AppInitializer = impl
}