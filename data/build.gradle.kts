plugins {
    alias(libs.plugins.kotlin.multiplatform)
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

            implementation(libs.kotlinx.datetime)
            implementation(libs.fleeksoft.ksoup)

            implementation(libs.kotlininject.runtime)
        }
    }
}