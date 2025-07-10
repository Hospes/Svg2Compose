package app.s2c.preferences

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.PreferencesSettings
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn
import java.util.prefs.Preferences

actual interface PreferencesPlatformComponent {
    @SingleIn(AppScope::class)
    @Provides
    fun provideSettings(delegate: Preferences): ObservableSettings = PreferencesSettings(delegate)

    @SingleIn(AppScope::class)
    @Provides
    fun providePreferences(): Preferences = Preferences.userRoot().node("app.s2c")
}
