plugins {
    id("app.s2c.kotlin.multiplatform")
    id("app.s2c.compose")
    id("app.s2c.metro")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.base)
            api(projects.core.preferences)
            api(projects.core.logging)
            api(projects.common.ui.compose)
            api(projects.common.ui.di)
            api(projects.common.ui.resources)
            implementation(projects.data)
            api(projects.ui.converter)
            api(projects.ui.config)

            implementation(compose.desktop.currentOs)

            implementation(libs.kotlinx.coroutines.core)
        }

        jvmMain.dependencies {
            api(libs.kotlinx.coroutines.swing)
        }
    }
}