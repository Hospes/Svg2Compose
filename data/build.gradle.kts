plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    applyDefaultHierarchyTemplate()

    jvm()

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.base)
                implementation(projects.core.preferences)
                api(libs.kotlinx.datetime)

                implementation(libs.kotlininject.runtime)
            }
        }
    }
}