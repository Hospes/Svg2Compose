package app.s2c.core.logs

import app.s2c.core.base.appinitializers.AppInitializer
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

actual interface LoggerPlatformComponent {
    @Provides
    @IntoSet
    fun provideLogbackInitializer(impl: LogbackInitializer): AppInitializer = impl
}