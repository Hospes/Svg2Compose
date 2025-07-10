package app.s2c.preferences

import app.s2c.core.base.inject.ApplicationScope
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

expect interface PreferencesPlatformComponent

@ContributesTo(AppScope::class)
interface PreferencesComponent : PreferencesPlatformComponent {
    val preferences: AppPreferences

    @SingleIn(AppScope::class)
    @Provides
    fun providePreferences(bind: AppPreferencesImpl): AppPreferences = bind
}
