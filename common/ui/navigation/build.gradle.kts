plugins {
    id("app.s2c.kotlin.multiplatform")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)

            api(libs.jetbrains.navigation3.ui)
        }
    }
}
