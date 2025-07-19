package app.s2c

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import androidx.lifecycle.ViewModelProvider
import app.s2c.common.LocalAppResources
import app.s2c.common.rememberAppResources
import app.s2c.di.AppGraph
import app.s2c.ui.di.LocalViewModelFactoryOwner
import app.s2c.ui.di.ViewModelFactoryOwner
import dev.zacsweers.metro.createGraph

fun main() {
    System.setProperty("skiko.renderApi", "SOFTWARE") //TODO: Fixes issue with G-Sync stuttering

    // Create an injection graph
    val appGraph = createGraph<AppGraph>()
    appGraph.initializers.initialize()

    application {
        CompositionLocalProvider(
            LocalAppResources provides rememberAppResources(),
            // Provide a way to access the ViewModel factory to injectedViewModel calls down the composable tree
            LocalViewModelFactoryOwner provides object : ViewModelFactoryOwner {
                override val viewModelFactory: ViewModelProvider.Factory get() = appGraph.vmFactory
            },
        ) {
            App(
                state = rememberAppState(exitApp = ::exitApplication)
            )
        }
    }
}