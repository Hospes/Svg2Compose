package app.s2c.core.logs

import app.s2c.core.base.appinitializers.AppInitializer
import dev.zacsweers.metro.*

expect interface LoggerPlatformComponent

@ContributesTo(AppScope::class)
interface LoggerComponent : LoggerPlatformComponent {
    @Provides
    @IntoSet
    fun provideLog4KInitializer(impl: Log4KInitializer): AppInitializer = impl
}