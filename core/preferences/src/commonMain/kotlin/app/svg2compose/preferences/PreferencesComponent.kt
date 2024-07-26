package app.svg2compose.preferences

import app.svg2compose.core.base.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

expect interface PreferencesPlatformComponent

interface PreferencesComponent : PreferencesPlatformComponent {
    val preferences: AppPreferences

    @ApplicationScope
    @Provides
    fun providePreferences(bind: AppPreferencesImpl): AppPreferences = bind
}
