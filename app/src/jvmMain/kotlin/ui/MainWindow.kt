package ui

import MainScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window

@Composable
fun MainWindow(
    exitApp: () -> Unit,
) {
    Window(
        title = "Svg2Compose",
        icon = painterResource("icon.png"),
        onCloseRequest = exitApp,
    ) {
        MainScreen()
    }
}