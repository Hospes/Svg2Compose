package app.svg2compose.core.logging.implementation

fun interface SetCrashReportingEnabledAction {
    operator fun invoke(enabled: Boolean)
}