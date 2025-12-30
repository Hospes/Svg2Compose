plugins {
    id("app.s2c.kotlin.multiplatform")
    id("app.s2c.compose")
    id("app.s2c.metro")
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.preferences)
            implementation(projects.core.logging)
            implementation(projects.data)

            implementation(projects.common.ui.compose)
            implementation(projects.common.ui.resources)
            implementation(projects.common.ui.di)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}