rootProject.name = "svg2compose"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("gradle/build-logic")

    repositories {
        google {
            mavenContent {
                includeGroupByRegex(".*google.*")
                includeGroupByRegex(".*android.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()

        // Prerelease versions of Compose Multiplatform
        // maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")

        // Used for snapshots if needed
        // maven("https://oss.sonatype.org/content/repositories/snapshots/")
        // maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupByRegex(".*google.*")
                includeGroupByRegex(".*android.*")
            }
        }
        mavenCentral()
        mavenLocal()

        // Prerelease versions of Compose Multiplatform
        // maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")

        // Used for snapshots if needed
        // maven("https://oss.sonatype.org/content/repositories/snapshots/")
        // maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

include(
    ":core:base",
    ":core:logging",
    ":core:preferences",

    ":common:ui:di",
    ":common:ui:compose",
    ":common:ui:resources",

    ":models",
    ":data",

    ":ui:converter",
    ":ui:config",

    "app",

    "desktop-app",
    "android-app",
)