package app.s2c.preferences

import dev.zacsweers.metro.*

expect interface PreferencesPlatformComponent

@ContributesTo(AppScope::class)
interface PreferencesComponent : PreferencesPlatformComponent {
    val preferences: AppPreferences

    @SingleIn(AppScope::class)
    @Provides
    fun providePreferences(bind: AppPreferencesImpl): AppPreferences = bind
}
