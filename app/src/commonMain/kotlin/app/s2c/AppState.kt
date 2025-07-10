package app.s2c

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.TrayState

@Composable
fun rememberAppState(exitApp: () -> Unit) = remember {
    AppState(exitApp = exitApp)
}

@Stable
class AppState(
    private val exitApp: () -> Unit
) {
    val tray = TrayState()

    fun exit() = exitApp()

    fun sendNotification(notification: Notification) {
        tray.sendNotification(notification)
    }
}