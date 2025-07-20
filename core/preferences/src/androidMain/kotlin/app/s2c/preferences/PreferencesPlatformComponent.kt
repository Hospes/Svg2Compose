package app.s2c.preferences

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import java.util.prefs.Preferences

actual interface PreferencesPlatformComponent {
    @SingleIn(AppScope::class)
    @Provides
    fun provideSettings(delegate: SharedPreferences): ObservableSettings = SharedPreferencesSettings(delegate)

    @SingleIn(AppScope::class)
    @Provides
    fun providePreferences(
        context: Application,
    ): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
}
