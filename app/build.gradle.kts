import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("app.s2c.kotlin.multiplatform")  //alias(libs.plugins.kotlin.multiplatform)
    id("app.s2c.compose")   //alias(libs.plugins.compose.multiplatform); alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.metro)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.preferences)
            implementation(projects.core.logging)
            implementation(projects.common.ui.compose)
            implementation(projects.common.ui.di)
            implementation(projects.data)
            implementation(projects.ui.converter)
            implementation(projects.ui.config)

            implementation(compose.desktop.currentOs)
            implementation(compose.material)
            implementation(compose.materialIconsExtended)

            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)

            implementation(libs.kotlinx.coroutines.core)
        }

        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

group = "svg2compose"
version = gitDescribe(project.providers).get()

compose.desktop {
    application {
        mainClass = "app.s2c.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Rpm)
            packageName = "Svg2Compose"
            packageVersion = gitDescribe(project.providers).get()
            windows {
                iconFile.set(File("../icon.ico"))
                menu = true
                perUserInstall = true
                upgradeUuid = "0DFB0005-59B7-4702-BD47-CED700CEB37C"
                includeAllModules = true
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


fun versionSuffix(providers: ProviderFactory): Provider<String> {
    return providers.exec {
        commandLine("git", "branch", "--show-current")
    }.standardOutput.asText.map { branch ->
        val branchName = branch.trim()
        when {
            branchName.matches("""release/(.+)""".toRegex()) -> "-RC"
            branchName.matches("""feature/(.+)""".toRegex()) -> "-FEATURE"
            else -> ""
        }
    }
}

fun gitDescribe(providers: ProviderFactory): Provider<String> {
    return providers.exec {
        // --abbrev=0 removes the commit hash suffix
        commandLine("git", "describe", "--tags", "--abbrev=0")
    }.standardOutput.asText.map { it.trim().replace(Regex("[^0-9.]"), "") }
}