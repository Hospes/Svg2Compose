package app.s2c.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.ViewModelProvider
import app.s2c.AppNavigation
import app.s2c.S2cActivity
import app.s2c.S2cApp
import app.s2c.ui.di.LocalViewModelFactoryOwner
import app.s2c.ui.di.ViewModelFactoryOwner
import app.s2c.ui.di.ViewModelGraph
import dev.zacsweers.metro.asContribution

class MainActivity : S2cActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appGraph = (application as S2cApp).appGraph

        setContent {
            val viewModelGraph = appGraph.asContribution<ViewModelGraph.Factory>().createViewModelGraph()

            CompositionLocalProvider(
                // Provide a way to access the ViewModel factory to injectedViewModel calls down the composable tree
                LocalViewModelFactoryOwner provides object : ViewModelFactoryOwner {
                    override val viewModelFactory: ViewModelProvider.Factory get() = viewModelGraph.vmFactory
                },
            ) {
                AppNavigation()
            }
        }
    }
}