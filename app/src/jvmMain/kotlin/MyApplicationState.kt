import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.TrayState

@Composable
fun rememberApplicationState(exitApp: () -> Unit) = remember {
    MyApplicationState(exitApp = exitApp)
}

class MyApplicationState(
    private val exitApp: () -> Unit
) {
    val tray = TrayState()

    fun exit() = exitApp()

    fun sendNotification(notification: Notification) {
        tray.sendNotification(notification)
    }
}