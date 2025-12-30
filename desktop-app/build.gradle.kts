import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("app.s2c.kotlin.multiplatform")
    id("app.s2c.compose")
    id("app.s2c.metro")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.app)

            implementation(compose.desktop.currentOs)

            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.jetbrains.lifecycle.viewmodel)

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
                iconFile.set(File("../icon.png"))
            }
            macOS {
                iconFile.set(File("../icon.icns"))
            }
        }

        buildTypes.release {
            proguard {
                configurationFiles.from(project.file("proguard-rules.pro"))
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