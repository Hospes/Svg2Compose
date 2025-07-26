plugins {
    id("app.s2c.android.application")
    id("app.s2c.kotlin.android")
    id("app.s2c.compose")
    alias(libs.plugins.metro)
}

android {
    namespace = "app.s2c"

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/INDEX.LIST"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)

    implementation(projects.app)

    implementation(libs.kotlinx.coroutines.android)
}