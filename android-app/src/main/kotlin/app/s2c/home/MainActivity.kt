package app.s2c.home

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import app.s2c.AppNavigation
import app.s2c.S2cActivity
import app.s2c.S2cApp
import app.s2c.ui.di.LocalMetroViewModelFactory

class MainActivity : S2cActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(DefaultLightScrim, DefaultDarkScrim),
        )
        super.onCreate(savedInstanceState)

        val appGraph = (application as S2cApp).appGraph

        setContent {
            CompositionLocalProvider(
                LocalMetroViewModelFactory provides appGraph.viewModelFactory,
            ) {
                AppNavigation()
            }
        }
    }
}