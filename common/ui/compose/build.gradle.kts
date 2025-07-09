plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting

        commonMain.dependencies {
            implementation(projects.core.base)
            //implementation(projects.data.models)

//            implementation(compose.runtime)
//            implementation(compose.foundation)
            implementation(compose.material3)
            api(compose.materialIconsExtended)
//            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
//
//            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.navigation.compose)
        }
    }
}
