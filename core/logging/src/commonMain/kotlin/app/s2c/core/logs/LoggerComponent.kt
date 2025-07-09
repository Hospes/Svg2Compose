package app.s2c.core.logs

import app.s2c.core.base.appinitializers.AppInitializer
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

expect interface LoggerPlatformComponent

interface LoggerComponent : LoggerPlatformComponent {
    @Provides
    @IntoSet
    fun provideLog4KInitializer(impl: Log4KInitializer): AppInitializer = impl
}