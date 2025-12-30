package app.s2c

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import app.s2c.ui.di.LocalMetroViewModelFactory
import dev.zacsweers.metro.createGraph

fun main() {
    //System.setProperty("skiko.renderApi", "OPENGL") //TODO: Fixes issue with G-Sync stuttering

    // Create an injection graph
    val appGraph = createGraph<DesktopAppGraph>()
    appGraph.initializers.initialize()

    application {
        CompositionLocalProvider(
            LocalMetroViewModelFactory provides appGraph.viewModelFactory,
        ) {
            App(
                state = rememberAppState(exitApp = ::exitApplication)
            )
        }
    }
}