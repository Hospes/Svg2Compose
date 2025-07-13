import app.s2c.gradle.addKspDependencyForAllTargets
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("app.s2c.kotlin.multiplatform")  //alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.ksp)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.core.preferences)
            implementation(libs.log4k)
            implementation(libs.log4k.slf4j)
            implementation(libs.kotlininject.runtime)
        }
        jvmMain.dependencies {
            implementation(libs.logback.classic)
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