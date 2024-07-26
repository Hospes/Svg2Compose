import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

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
                api(libs.multiplatformsettings.core)
                api(libs.multiplatformsettings.coroutines)
            }
        }
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}