package app.s2c.preferences

import app.s2c.core.base.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides

expect interface PreferencesPlatformComponent

interface PreferencesComponent : PreferencesPlatformComponent {
    val preferences: AppPreferences

    @ApplicationScope
    @Provides
    fun providePreferences(bind: AppPreferencesImpl): AppPreferences = bind
}
