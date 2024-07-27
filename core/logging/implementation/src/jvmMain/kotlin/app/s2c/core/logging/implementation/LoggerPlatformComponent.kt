package app.s2c.core.logging.implementation

import app.s2c.core.base.inject.ApplicationScope
import app.s2c.core.logging.api.Logger
import app.s2c.core.logging.api.RecordingLogger
import me.tatarka.inject.annotations.Provides

actual interface LoggerPlatformComponent {
    @Provides
    @ApplicationScope
    fun provideLogger(
        kermitLogger: KermitLogger,
        recordingLogger: RecordingLogger,
    ): Logger = CompositeLogger(kermitLogger, recordingLogger)

    @Provides
    fun bindSetCrashReportingEnabledAction(): SetCrashReportingEnabledAction {
        return NoopSetCrashReportingEnabledAction
    }
}

private object NoopSetCrashReportingEnabledAction : SetCrashReportingEnabledAction {
    override fun invoke(enabled: Boolean) {}
}