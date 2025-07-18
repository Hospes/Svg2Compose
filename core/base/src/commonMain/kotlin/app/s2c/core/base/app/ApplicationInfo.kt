package app.s2c.core.base.app

/**
 * Contains essential information about the application configuration and metadata.
 *
 * This data class holds various application properties including package information,
 * build configuration, versioning details, and runtime settings.
 *
 * @property packageName The unique package identifier for the application
 * @property debugBuild Whether this is a debug build of the application
 * @property flavor The build flavor/variant of the application
 * @property versionName The human-readable version string
 * @property versionCode The numeric version code for the application
 * @property cachePath A function that returns the path to the application's cache directory
 */
data class ApplicationInfo(
    val packageName: String,
    val debugBuild: Boolean,
    val flavor: Flavor,
    val versionName: String,
    val versionCode: Int,
    val cachePath: () -> String,
)

/**
 * Represents the different build flavors available for the application.
 *
 * Build flavors allow for different configurations and feature sets
 * within the same application codebase.
 */
enum class Flavor {
    Qa,
    Standard,
}
