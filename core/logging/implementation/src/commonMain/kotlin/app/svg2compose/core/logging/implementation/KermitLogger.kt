package app.svg2compose.core.logging.implementation

import app.svg2compose.core.base.app.ApplicationInfo
import app.svg2compose.core.base.app.Flavor
import app.svg2compose.core.logging.api.Logger
import co.touchlab.kermit.Severity
import me.tatarka.inject.annotations.Inject
import co.touchlab.kermit.Logger as Kermit

@Inject
class KermitLogger(applicationInfo: ApplicationInfo) : Logger {
    init {
        Kermit.setMinSeverity(
            when {
                applicationInfo.debugBuild -> Severity.Debug
                applicationInfo.flavor == Flavor.Qa -> Severity.Debug
                else -> Severity.Error
            },
        )
    }

    override fun v(throwable: Throwable?, message: () -> String) {
        if (throwable != null) {
            Kermit.v(throwable = throwable, message = message)
        } else {
            Kermit.v(message = message)
        }
    }

    override fun d(throwable: Throwable?, message: () -> String) {
        if (throwable != null) {
            Kermit.d(throwable = throwable, message = message)
        } else {
            Kermit.d(message = message)
        }
    }

    override fun i(throwable: Throwable?, message: () -> String) {
        if (throwable != null) {
            Kermit.i(throwable = throwable, message = message)
        } else {
            Kermit.i(message = message)
        }
    }

    override fun e(throwable: Throwable?, message: () -> String) {
        if (throwable != null) {
            Kermit.e(throwable = throwable, message = message)
        } else {
            Kermit.e(message = message)
        }
    }

    override fun w(throwable: Throwable?, message: () -> String) {
        if (throwable != null) {
            Kermit.w(throwable = throwable, message = message)
        } else {
            Kermit.w(message = message)
        }
    }
}