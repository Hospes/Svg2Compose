package app.s2c.gradle

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun Project.configureAndroidLegacy() {
    androidLegacy {
        compileSdkVersion(Versions.COMPILE_SDK)

        defaultConfig {
            minSdk = Versions.MIN_SDK
            targetSdk = Versions.TARGET_SDK
        }

        compileOptions {
            // https://developer.android.com/studio/write/java8-support
            isCoreLibraryDesugaringEnabled = true
        }
    }

    dependencies {
        // https://developer.android.com/studio/write/java8-support
        "coreLibraryDesugaring"(libs.findLibrary("tools.desugarjdklibs").get())
    }
}

fun Project.configureAndroidLibrary() {
    kotlin {
        android {
            compileSdk = Versions.COMPILE_SDK
            minSdk = Versions.MIN_SDK

            // https://developer.android.com/studio/write/java8-support
            enableCoreLibraryDesugaring = true
        }
    }

    dependencies {
        // https://developer.android.com/studio/write/java8-support
        "coreLibraryDesugaring"(libs.findLibrary("tools.desugarjdklibs").get())
    }
}

private fun Project.androidLegacy(action: ApplicationExtension.() -> Unit) = extensions.configure<ApplicationExtension>(action)

fun Project.kotlin(configure: KotlinMultiplatformExtension.() -> Unit) =
    extensions.configure<KotlinMultiplatformExtension>(configure)

private fun KotlinMultiplatformExtension.android(configure: KotlinMultiplatformAndroidLibraryTarget.() -> Unit) =
    extensions.configure<KotlinMultiplatformAndroidLibraryTarget>(configure)