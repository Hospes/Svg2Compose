package app.s2c

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import app.s2c.ui.common.theme.AppTheme
import app.s2c.ui.config.ConfigDialog
import app.s2c.ui.config.addConfigDialog
import app.s2c.ui.converter.ConverterScreen
import app.s2c.ui.converter.addConverterScreen
import app.s2c.ui.navigation.Navigator
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
) {
    AppTheme {
//        NavHost(
//            navController = navController,
//            startDestination = ConverterScreen,
//            modifier = modifier,
//        ) {
//            addConverterScreen(
//                navController = navController,
//                navigateConfig = { navController.navigate(ConfigDialog()) },
//            )
//            addConfigDialog(navController = navController)
//        }

        val backStack = rememberNavBackStack(configuration = config, ConverterScreen)
        val navigator = remember(backStack) { NavigatorImpl(backStack) }
        val dialogStrategy = remember { DialogSceneStrategy<NavKey>() }

        NavDisplay(
            backStack = backStack,
            sceneStrategy = dialogStrategy,
            entryProvider = entryProvider {
                addConverterScreen(
                    navigator = navigator,
                    navigateConfig = { navigator.navigateTo(ConfigDialog()) },
                )
                addConfigDialog(navigator = navigator)
            },
            modifier = modifier,
        )
    }
}

private class NavigatorImpl(
    val backStack: NavBackStack<NavKey>,
) : Navigator {
    override fun navigateUp() {
        backStack.removeLastOrNull()
    }

    override fun navigateTo(key: NavKey) {
        backStack.add(key)
    }
}

// Creates the required serializing configuration for open polymorphism
private val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(ConverterScreen::class, ConverterScreen.serializer())
            subclass(ConfigDialog::class, ConfigDialog.serializer())
        }
    }
}