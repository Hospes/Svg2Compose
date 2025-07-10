package app.s2c

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import app.s2c.common.LocalAppResources
import app.s2c.ui.common.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun ApplicationScope.App(state: AppState) {
    ApplicationTray(state)

    AppTheme {
        Window(
            state = rememberWindowState(width = 1280.dp, height = 800.dp),
            title = "Svg2Compose",
            icon = painterResource("icon.png"),
            onCloseRequest = state::exit,
        ) {
            //MainScreen()
            AppNavigation()
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