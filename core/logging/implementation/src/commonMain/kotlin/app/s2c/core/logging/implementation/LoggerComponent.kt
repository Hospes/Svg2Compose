package app.s2c.core.logging.implementation

import app.s2c.core.base.app.ApplicationInfo
import app.s2c.core.base.app.Flavor
import app.s2c.core.base.appinitializers.AppInitializer
import app.s2c.core.base.inject.ApplicationScope
import app.s2c.core.logging.api.RecordingLogger
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

expect interface LoggerPlatformComponent

interface LoggerComponent : LoggerPlatformComponent {
    @ApplicationScope
    @Provides
    fun bindRecordingLogger(
        applicationInfo: ApplicationInfo,
    ): RecordingLogger = when {
        applicationInfo.debugBuild -> RecordingLoggerImpl()
        applicationInfo.flavor == Flavor.Qa -> RecordingLoggerImpl()
        else -> NoopRecordingLogger
    }

    @Provides
    @IntoSet
    fun provideCrashReportingInitializer(impl: CrashReportingInitializer): AppInitializer = impl
}
