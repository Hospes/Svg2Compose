package app.s2c

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.MenuScope
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import app.s2c.common.LocalAppResources
import app.s2c.ui.MainNavigation
import app.s2c.ui.common.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun ApplicationScope.App(state: AppState) {
    ApplicationTray(state)

    AppTheme {
        Window(
            title = "Svg2Compose",
            icon = painterResource("icon.png"),
            onCloseRequest = state::exit,
        ) {
            //MainScreen()
            MainNavigation()
        }
    }
}

@Composable
private fun ApplicationScope.ApplicationTray(state: AppState) {
    Tray(
        LocalAppResources.current.icon,
        state = state.tray,
        tooltip = "Notepad",
        menu = { ApplicationMenu(state) }
    )
}

@Composable
private fun MenuScope.ApplicationMenu(state: AppState) {
    val scope = rememberCoroutineScope()
    fun exit() = scope.launch { state.exit() }

    //Item("New", onClick = state::newWindow)
    //Separator()
    Item("Exit", onClick = { exit() })
}