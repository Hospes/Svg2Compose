package app.svg2compose.core.logging.implementation

import app.svg2compose.core.base.appinitializers.AppInitializer
import app.svg2compose.core.base.inject.ApplicationCoroutineScope
import app.svg2compose.preferences.AppPreferences
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@Inject
class CrashReportingInitializer(
    private val preferences: Lazy<AppPreferences>,
    private val action: Lazy<SetCrashReportingEnabledAction>,
    private val scope: ApplicationCoroutineScope,
) : AppInitializer {
    override fun initialize() {
        scope.launch {
            preferences.value.reportAppCrashes.flow
                .collect { action.value(it) }
        }
    }
}
