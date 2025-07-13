plugins {
    id("app.s2c.kotlin.multiplatform")  //alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
            api(libs.kotlinx.atomicfu)
            api(libs.kotlininject.runtime)
            api(libs.kotlininject.anvil.runtime)
            api(libs.kotlininject.anvil.runtime.optional)
        }
    }
}