package app.s2c.core.base.appinitializers

fun interface AppInitializer {
    fun initialize()
}

interface AppSuspendedInitializer {
    suspend fun initialize()
}