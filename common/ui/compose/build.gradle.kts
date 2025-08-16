plugins {
    id("app.s2c.kotlin.multiplatform")  //alias(libs.plugins.kotlin.multiplatform)
    id("app.s2c.compose")   //alias(libs.plugins.compose.multiplatform); alias(libs.plugins.kotlin.compose.compiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.common.ui.resources)

            //implementation(compose.material3)
            api("org.jetbrains.compose.material3:material3:1.9.0-alpha04")
            api(compose.materialIconsExtended)
            implementation(compose.components.uiToolingPreview)
//
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.navigation.compose)
        }
    }
}
