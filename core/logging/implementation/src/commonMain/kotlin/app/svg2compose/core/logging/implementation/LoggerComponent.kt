package app.svg2compose.core.logging.implementation

import app.svg2compose.core.base.app.ApplicationInfo
import app.svg2compose.core.base.app.Flavor
import app.svg2compose.core.base.appinitializers.AppInitializer
import app.svg2compose.core.base.inject.ApplicationScope
import app.svg2compose.core.logging.api.RecordingLogger
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
