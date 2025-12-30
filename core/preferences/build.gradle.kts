plugins {
    id("app.s2c.kotlin.multiplatform")
    id("app.s2c.android.library")
    id("app.s2c.metro")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    android {
        namespace = "app.s2c.preferences"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.models)
            implementation(libs.kotlinx.serialization.json)
            api(libs.multiplatformsettings.core)
            api(libs.multiplatformsettings.coroutines)
        }
    }
}