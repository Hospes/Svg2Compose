import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.application
import ua.hospes.svg2compose.common.LocalAppResources
import ua.hospes.svg2compose.common.rememberAppResources

fun main() = application {
    System.setProperty("skiko.renderApi", "SOFTWARE") //TODO: Fixes issue with G-Sync stuttering

    CompositionLocalProvider(LocalAppResources provides rememberAppResources()) {
        MyApplication(
            state = rememberApplicationState(exitApp = ::exitApplication)
        )
    }
}
