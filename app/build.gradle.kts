import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.ksp)
}

kotlin {
    applyDefaultHierarchyTemplate()

    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.base)

                implementation(compose.desktop.currentOs)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                //implementation(libs.kotlinx.coroutines.swing)

                implementation(projects.data)

                implementation(libs.kotlininject.viewmodel.runtime)
                implementation(libs.kotlininject.viewmodel.compose)
                
                implementation(libs.androidx.lifecycle.viewmodel.compose)
            }
        }

        jvmMain {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                implementation(compose.preview)
                //implementation(libs.kotlinx.coroutines.swing)
            }
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


val v = "1.1.0"
group = "svg2compose"
version = v

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "ua.hospes.svg2compose"
            packageVersion = v
            windows {
                iconFile.set(File("icon.ico"))
                menuGroup = "start-menu-group"
                upgradeUuid = "0DFB0005-59B7-4702-BD47-CED700CEB37C"
            }
            linux {
                iconFile.set(File("icon.png"))
            }
            macOS {
                iconFile.set(File("icon.icns"))
            }
        }
    }
}