import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.MenuScope
import androidx.compose.ui.window.Tray
import common.LocalAppResources
import kotlinx.coroutines.launch
import theme.MyTheme
import ui.MainWindow

@Composable
fun ApplicationScope.MyApplication(state: MyApplicationState) {
    ApplicationTray(state)

    MyTheme {
        MainWindow(
            exitApp = state::exit,
        )
    }
}

@Composable
private fun ApplicationScope.ApplicationTray(state: MyApplicationState) {
    Tray(
        LocalAppResources.current.icon,
        state = state.tray,
        tooltip = "Notepad",
        menu = { ApplicationMenu(state) }
    )
}

@Composable
private fun MenuScope.ApplicationMenu(state: MyApplicationState) {
    val scope = rememberCoroutineScope()
    fun exit() = scope.launch { state.exit() }

    //Item("New", onClick = state::newWindow)
    //Separator()
    Item("Exit", onClick = { exit() })
}