plugins {
    id("app.s2c.root")

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.kotlin.compose.compiler) apply false
    alias(libs.plugins.metro) apply false
}

// Remove also build folder in root folder
tasks.register<Delete>("clean") {
    delete.add(rootProject.layout.buildDirectory)
}