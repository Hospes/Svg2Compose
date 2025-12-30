plugins {
    id("app.s2c.kotlin.multiplatform")
    id("app.s2c.android.library")
    id("app.s2c.compose") 
}

kotlin {
    android {
        namespace = "app.s2c.ui.resources"

        androidResources { enable = true }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.jetbrains.compose.runtime)
            api(libs.jetbrains.compose.resources)
            api(libs.jetbrains.compose.icons.extended)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "app.s2c.ui.resources"
    generateResClass = always
}