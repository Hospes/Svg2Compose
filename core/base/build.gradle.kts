plugins {
    id("app.s2c.kotlin.multiplatform")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.atomicfu)
        }
    }
}