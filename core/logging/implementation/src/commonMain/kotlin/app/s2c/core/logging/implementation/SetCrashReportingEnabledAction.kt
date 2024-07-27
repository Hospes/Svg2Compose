package app.s2c.core.logging.implementation

fun interface SetCrashReportingEnabledAction {
    operator fun invoke(enabled: Boolean)
}