package app.s2c

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import app.s2c.ui.common.theme.AppTheme
import app.s2c.ui.resources.Res
import app.s2c.ui.resources.icon
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun ApplicationScope.App(state: AppState) {
    ApplicationTray(state)

    AppTheme {
        Window(
            state = rememberWindowState(width = 1280.dp, height = 800.dp),
            title = "Svg2Compose",
            icon = painterResource(Res.drawable.icon),
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
        icon = painterResource(Res.drawable.icon),
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