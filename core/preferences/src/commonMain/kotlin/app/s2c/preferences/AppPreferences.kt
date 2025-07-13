package app.s2c.preferences

import app.s2c.models.ParserConfig

interface AppPreferences {

    val theme: Preference<Theme>

    val loglevel: Preference<LogLevel>
    val fileLoglevel: Preference<LogLevel>

    val parserConfig: Preference<ParserConfig>

    enum class LogLevel {
        DEBUG, INFO, WARNING, ERROR;

        companion object {
            internal fun toInt(level: LogLevel): Int = level.ordinal
            internal fun fromInt(ordinal: Int): LogLevel = LogLevel.entries[ordinal % LogLevel.entries.size]
        }
    }

    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM,
    }
}
