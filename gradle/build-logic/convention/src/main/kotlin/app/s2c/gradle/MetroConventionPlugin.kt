package app.s2c.gradle

import dev.zacsweers.metro.gradle.DelicateMetroGradleApi
import dev.zacsweers.metro.gradle.MetroPluginExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class MetroConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dev.zacsweers.metro")
            }

            configureMetro()
        }
    }
}

@OptIn(DelicateMetroGradleApi::class)
private fun Project.configureMetro() {
    metro {
        generateContributionHintsInFir.set(true)
        enableKotlinVersionCompatibilityChecks.set(false)
    }
}

private fun Project.metro(action: MetroPluginExtension.() -> Unit) = extensions.configure<MetroPluginExtension>(action)