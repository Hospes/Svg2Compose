package app.s2c.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.kotlin.multiplatform.library")    // TODO: https://developer.android.com/kotlin/multiplatform/plugin
                //apply("org.gradle.android.cache-fix")
            }

            configureAndroidLibrary()
        }
    }
}