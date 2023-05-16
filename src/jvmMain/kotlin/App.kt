import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    System.setProperty("skiko.renderApi", "SOFTWARE") //TODO: Fixes issue with G-Sync stuttering
    Window(
        title = "Svg2Compose",
        icon = painterResource("icon.png"),
        onCloseRequest = ::exitApplication,
    ) {
        MainScreen()
    }
}
