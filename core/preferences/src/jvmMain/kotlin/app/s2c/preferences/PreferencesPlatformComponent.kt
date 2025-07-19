package app.s2c.preferences

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import java.util.prefs.Preferences

actual interface PreferencesPlatformComponent {
    @SingleIn(AppScope::class)
    @Provides
    fun provideSettings(delegate: Preferences): ObservableSettings = PreferencesSettings(delegate)

    @SingleIn(AppScope::class)
    @Provides
    fun providePreferences(): Preferences = Preferences.userRoot().node("app.s2c")
}
