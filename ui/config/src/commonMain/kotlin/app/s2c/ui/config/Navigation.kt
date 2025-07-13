package app.s2c.ui.config

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog

fun NavGraphBuilder.addConfigDialog(
    navController: NavController,
) {
    dialog<ConfigDialog> {
        ConfigDialog(
            navigateUp = navController::navigateUp,
        )
    }
}