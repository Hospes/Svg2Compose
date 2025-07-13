plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.build.config)
}

buildConfig {
    packageName("app.s2c.data")

    buildConfigField("DEBUG", false)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.preferences)
            implementation(projects.core.logging)
            api(projects.models)

            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.fleeksoft.ksoup)

            implementation(libs.kotlininject.runtime)
        }
    }
}