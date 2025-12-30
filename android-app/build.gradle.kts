plugins {
    id("app.s2c.android.application")
    alias(libs.plugins.kotlin.compose.compiler)
    id("app.s2c.metro")
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