plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.composeCompiler.gradlePlugin)
    compileOnly(libs.metro.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform") {
            id = "app.s2c.kotlin.multiplatform"
            implementationClass = "app.s2c.gradle.KotlinMultiplatformConventionPlugin"
        }

        register("root") {
            id = "app.s2c.root"
            implementationClass = "app.s2c.gradle.RootConventionPlugin"
        }

        register("androidApplication") {
            id = "app.s2c.android.application"
            implementationClass = "app.s2c.gradle.AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "app.s2c.android.library"
            implementationClass = "app.s2c.gradle.AndroidLibraryConventionPlugin"
        }

        register("compose") {
            id = "app.s2c.compose"
            implementationClass = "app.s2c.gradle.ComposeMultiplatformConventionPlugin"
        }

        register("metro") {
            id = "app.s2c.metro"
            implementationClass = "app.s2c.gradle.MetroConventionPlugin"
        }
    }
}