package app.s2c

import android.app.Application
import dev.zacsweers.metro.createGraphFactory

class S2cApp : Application() {

    // Create an injection graph lazily
    val appGraph: AndroidAppGraph by lazy { createGraphFactory<AndroidAppGraph.Factory>().create(this) }

    override fun onCreate() {
        super.onCreate()

        appGraph.initializers.initialize()
    }
}