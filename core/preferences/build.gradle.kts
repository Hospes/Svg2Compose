plugins {
    id("app.s2c.kotlin.multiplatform")  //alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.metro)
}

kotlin {
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