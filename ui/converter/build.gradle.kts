import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.preferences)
            implementation(projects.core.logging)
            implementation(projects.data)

            implementation(projects.common.ui.compose)
            implementation(projects.common.ui.resources)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlininject.runtime)
            implementation(libs.kotlininject.viewmodel.runtime)
            implementation(libs.kotlininject.viewmodel.compose)

            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.navigation.compose)
        }
    }
}

addKspDependencyForAllTargets(libs.kotlininject.compiler)
addKspDependencyForAllTargets(libs.kotlininject.anvil.compiler)
addKspDependencyForAllTargets(libs.kotlininject.viewmodel.compiler)


fun Project.addKspDependencyForAllTargets(dependencyNotation: Any) =
    addKspDependencyForAllTargets("", dependencyNotation)

private fun Project.addKspDependencyForAllTargets(
    configurationNameSuffix: String,
    dependencyNotation: Any,
) {
    val kmpExtension = extensions.getByType<KotlinMultiplatformExtension>()
    dependencies {
        kmpExtension.targets
            .asSequence()
            .filter { target ->
                // Don't add KSP for common target, only final platforms
                target.platformType != KotlinPlatformType.common
            }
            .forEach { target ->
                add(
                    "ksp${target.targetName.capitalized()}$configurationNameSuffix",
                    dependencyNotation,
                )
            }
    }
}