package app.s2c.preferences

import app.s2c.core.base.inject.ApplicationScope
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import me.tatarka.inject.annotations.Provides
import java.util.prefs.Preferences

actual interface PreferencesPlatformComponent {
    @ApplicationScope
    @Provides
    fun provideSettings(delegate: Preferences): ObservableSettings = PreferencesSettings(delegate)
}
