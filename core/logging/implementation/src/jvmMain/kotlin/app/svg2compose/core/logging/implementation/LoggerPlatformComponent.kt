package app.svg2compose.core.logging.implementation

import app.svg2compose.core.base.inject.ApplicationScope
import app.svg2compose.core.logging.api.Logger
import app.svg2compose.core.logging.api.RecordingLogger
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