import app.s2c.gradle.addKspDependencyForAllTargets
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("app.s2c.kotlin.multiplatform")  //alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            api(libs.multiplatformsettings.core)
            api(libs.multiplatformsettings.coroutines)
            implementation(libs.kotlinx.serialization.json)
            implementation(projects.models)
        }
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

addKspDependencyForAllTargets(libs.kotlininject.compiler)
addKspDependencyForAllTargets(libs.kotlininject.anvil.compiler)
//addKspDependencyForAllTargets(libs.kotlininject.viewmodel.compiler)