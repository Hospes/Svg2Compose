plugins {
    id("app.s2c.kotlin.multiplatform")
    id("app.s2c.android.library")
    id("app.s2c.metro")
}

kotlin {
    android {
        namespace = "app.s2c.core.logs"
    }
    
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