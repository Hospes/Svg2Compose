plugins {
    id("app.s2c.kotlin.multiplatform")
    id("app.s2c.compose")
    id("app.s2c.metro")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)

            api(libs.jetbrains.lifecycle.runtime)
            api(libs.jetbrains.lifecycle.viewmodel)
            api(libs.jetbrains.navigation.compose)
        }
    }
}
