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
                api(projects.core.base)
                api(projects.core.logging.api)
                api(projects.core.preferences)
                api(libs.kotlinx.coroutines.core)
                implementation(libs.kermit.kermit)
                implementation(libs.kotlininject.runtime)
            }
        }
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}