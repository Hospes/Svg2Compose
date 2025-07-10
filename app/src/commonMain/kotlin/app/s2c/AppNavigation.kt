package app.s2c

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.s2c.ui.converter.ConverterScreen
import app.s2c.ui.converter.addConverterScreen

@Composable
internal fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = ConverterScreen,
        modifier = modifier,
    ) {
        addConverterScreen(navController = navController)
    }
}