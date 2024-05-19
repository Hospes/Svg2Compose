import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
}

val v = "1.1.0"
group = "svg2compose"
version = v

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
}

kotlin {
    jvm()
    jvmToolchain { languageVersion.set(JavaLanguageVersion.of(17)) }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
            }
        }
        //val jvmTest by getting

        all {
            languageSettings.optIn("androidx.compose.ui.ExperimentalComposeUiApi")
            languageSettings.optIn("androidx.compose.material.ExperimentalMaterialApi")
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "svg2compose"
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
