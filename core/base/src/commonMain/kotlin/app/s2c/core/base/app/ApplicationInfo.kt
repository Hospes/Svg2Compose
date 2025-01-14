package app.s2c.core.base.app

data class ApplicationInfo(
    val packageName: String,
    val debugBuild: Boolean,
    val flavor: Flavor,
    val versionName: String,
    val versionCode: Int,
    val cachePath: () -> String,
)

enum class Flavor {
  Qa,
  Standard,
}
