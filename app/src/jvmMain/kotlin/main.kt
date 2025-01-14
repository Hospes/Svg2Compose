import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import app.s2c.common.LocalAppResources
import app.s2c.common.rememberAppResources

fun main() = application {
    System.setProperty("skiko.renderApi", "SOFTWARE") //TODO: Fixes issue with G-Sync stuttering

    CompositionLocalProvider(LocalAppResources provides rememberAppResources()) {
        MyApplication(
            state = rememberApplicationState(exitApp = ::exitApplication)
        )
    }
}
