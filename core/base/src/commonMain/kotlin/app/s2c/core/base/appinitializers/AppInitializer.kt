package app.s2c.core.base.appinitializers

/**
 * Functional interface for application initialization tasks.
 *
 * Implementations of this interface should perform synchronous initialization
 * work that needs to be completed during application startup.
 */
fun interface AppInitializer {
    /**
     * Performs the initialization task.
     *
     * This method is called during application startup to execute
     * the specific initialization logic implemented by this initializer.
     */
    fun initialize()
}

/**
 * Interface for application initialization tasks that require suspension.
 *
 * Use this interface when initialization work involves asynchronous operations
 * such as network calls, file I/O, or other suspending functions.
 */
interface AppSuspendedInitializer {
    /**
     * Performs the asynchronous initialization task.
     *
     * This suspending method is called during application startup to execute
     * initialization logic that may involve asynchronous operations.
     */
    suspend fun initialize()
}