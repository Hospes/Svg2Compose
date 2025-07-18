package app.s2c.core.base.util

import kotlinx.coroutines.CancellationException

/**
 * Executes the given block and returns a Result, properly handling CancellationException.
 *
 * This function is similar to Kotlin's runCatching but ensures that CancellationException
 * is not caught and wrapped in a Result.failure, allowing proper cancellation propagation
 * in coroutine contexts.
 *
 * @param block The block of code to execute
 * @return Result.success with the block's return value, or Result.failure with any non-cancellation exception
 * @throws CancellationException if the block throws a CancellationException (not caught)
 */
inline fun <T, R> T.cancellableRunCatching(block: T.() -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (ce: CancellationException) {
        throw ce
    } catch (e: Throwable) {
        Result.failure(e)
    }
}
