package app.s2c.core.logs

import app.s2c.core.base.appinitializers.AppInitializer
import me.tatarka.inject.annotations.Inject
import saschpe.log4k.slf4j.SLF4JLogger

@Inject
class Log4KInitializer : AppInitializer {
    override fun initialize() {
        saschpe.log4k.Log.loggers.clear()
        saschpe.log4k.Log.loggers += SLF4JLogger()
    }
}