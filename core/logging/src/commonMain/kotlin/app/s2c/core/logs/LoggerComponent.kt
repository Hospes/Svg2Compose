package app.s2c.core.logs

import app.s2c.core.base.appinitializers.AppInitializer
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

expect interface LoggerPlatformComponent

@ContributesTo(AppScope::class)
interface LoggerComponent : LoggerPlatformComponent {
    @Provides
    @IntoSet
    fun provideLog4KInitializer(impl: Log4KInitializer): AppInitializer = impl
}