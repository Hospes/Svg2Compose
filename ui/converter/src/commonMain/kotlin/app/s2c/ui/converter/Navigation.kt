package app.s2c.ui.converter

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.addConverterScreen(
    navController: NavController,
) {
    composable<ConverterScreen> {
        ConverterScreen()
    }
}