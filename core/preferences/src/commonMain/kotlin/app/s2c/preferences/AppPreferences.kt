package app.s2c.preferences

import app.s2c.models.ParserConfig

/**
 * Interface for managing application preferences and settings.
 *
 * Provides access to various user preferences including theme settings,
 * logging configuration, and parser options.
 */
interface AppPreferences {

    /**
     * User's preferred theme setting for the application.
     */
    val theme: Preference<Theme>

    /**
     * The minimum log level for console/standard output logging.
     */
    val loglevel: Preference<LogLevel>
    /**
     * The minimum log level for file-based logging.
     */
    val fileLoglevel: Preference<LogLevel>

    /**
     * Configuration settings for the SVG parser.
     */
    val parserConfig: Preference<ParserConfig>

    /**
     * Represents the different logging levels available in the application.
     *
     * Log levels are ordered from most verbose (DEBUG) to least verbose (ERROR).
     */
    enum class LogLevel {
        DEBUG, INFO, WARNING, ERROR;

        companion object {
            /**
             * Converts a LogLevel enum value to its corresponding integer ordinal.
             *
             * @param level The LogLevel to convert
             * @return The integer ordinal value of the LogLevel
             */
            internal fun toInt(level: LogLevel): Int = level.ordinal
            /**
             * Converts an integer ordinal to its corresponding LogLevel enum value.
             *
             * @param ordinal The integer ordinal to convert
             * @return The LogLevel corresponding to the ordinal, with wraparound for invalid values
             */
            internal fun fromInt(ordinal: Int): LogLevel = LogLevel.entries[ordinal % LogLevel.entries.size]
        }
    }

    /**
     * Represents the available theme options for the application.
     *
     * Supports light, dark, and system-based theme selection.
     */
    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM,
    }
}
