package app.s2c.preferences

interface AppPreferences {

    val theme: Preference<Theme>
    val useDynamicColors: Preference<Boolean>

    val useLessData: Preference<Boolean>

    val libraryFollowedActive: Preference<Boolean>

    val upNextFollowedOnly: Preference<Boolean>

    val ignoreSpecials: Preference<Boolean>
    val reportAppCrashes: Preference<Boolean>
    val reportAnalytics: Preference<Boolean>

    val developerHideArtwork: Preference<Boolean>

    val episodeAiringNotificationsEnabled: Preference<Boolean>

    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM,
    }
}
