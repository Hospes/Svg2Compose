plugins {
    id("app.s2c.kotlin.multiplatform")
    id("app.s2c.compose")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            api(projects.common.ui.resources)

            api(libs.jetbrains.compose.runtime)
            api(libs.jetbrains.compose.ui)
            api(libs.jetbrains.compose.ui.tooling.preview)
            api(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.lifecycle.runtime)
            implementation(libs.jetbrains.navigation.compose)
        }
    }
}
