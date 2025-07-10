package app.s2c

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import androidx.lifecycle.ViewModelProvider
import app.s2c.common.LocalAppResources
import app.s2c.common.rememberAppResources
import app.s2c.di.AppComponent
import app.s2c.di.create
import com.teobaranga.kotlin.inject.viewmodel.runtime.compose.LocalViewModelFactoryOwner
import com.teobaranga.kotlin.inject.viewmodel.runtime.compose.ViewModelFactoryOwner

fun main() {
    System.setProperty("skiko.renderApi", "SOFTWARE") //TODO: Fixes issue with G-Sync stuttering

    // Create an injection graph
    val appComponent = AppComponent::class.create()

    application {
        CompositionLocalProvider(
            LocalAppResources provides rememberAppResources(),
            // Provide a way to access the ViewModel factory to injectedViewModel calls down the composable tree
            LocalViewModelFactoryOwner provides object : ViewModelFactoryOwner {
                override val viewModelFactory: ViewModelProvider.Factory get() = appComponent.vmFactory
            },
        ) {
            App(
                state = rememberAppState(exitApp = ::exitApplication)
            )
        }
    }
}