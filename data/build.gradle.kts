plugins {
    alias(libs.plugins.kotlin.multiplatform)
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