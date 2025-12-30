package app.s2c.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeMultiplatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.compose")
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
        configureCompose()
    }
}

fun Project.configureCompose() {
    composeCompiler {
        // Needed for Layout Inspector to be able to see all of the nodes in the component tree:
        //https://issuetracker.google.com/issues/338842143
        includeSourceInformation.set(true)

        if (project.providers.gradleProperty("tivi.enableComposeCompilerReports").isPresent) {
            val composeReports = layout.buildDirectory.map { it.dir("reports").dir("compose") }
            reportsDestination.set(composeReports)
            metricsDestination.set(composeReports)
        }

        // If we want to add a 3rd party class as stable/immutable for compose, we need to enable this
        //stabilityConfigurationFiles.addAll(
        //    project.layout.projectDirectory.file("compose-stability.conf"),
        //)
    }

    extensions.configure<KotlinMultiplatformExtension> {
        sourceSets.all {
            //languageSettings.optIn("androidx.compose.ui.ExperimentalComposeUiApi")
            //languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
        }
    }
}

fun Project.composeCompiler(block: ComposeCompilerGradlePluginExtension.() -> Unit) {
    extensions.configure<ComposeCompilerGradlePluginExtension>(block)
}