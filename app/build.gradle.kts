import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose.plugin)
    alias(libs.plugins.compose.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                //implementation(libs.kotlinx.coroutines.swing)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                implementation(compose.preview)
            //implementation(libs.kotlinx.coroutines.swing)
            }
        }
        //val jvmTest by getting

//        all {
//            languageSettings.optIn("androidx.compose.ui.ExperimentalComposeUiApi")
//            languageSettings.optIn("androidx.compose.material.ExperimentalMaterialApi")
//        }
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