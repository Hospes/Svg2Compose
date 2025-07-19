plugins {
    id("app.s2c.kotlin.multiplatform")  //alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.metro)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.preferences)
            implementation(libs.log4k)
            implementation(libs.log4k.slf4j)
        }
        jvmMain.dependencies {
            implementation(libs.logback.classic)
        }
    }
}