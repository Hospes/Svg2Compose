package app.svg2compose.core.base.util

import kotlinx.coroutines.CancellationException

inline fun <T, R> T.cancellableRunCatching(block: T.() -> R): Result<R> {
  return try {
    Result.success(block())
  } catch (ce: CancellationException) {
    throw ce
  } catch (e: Throwable) {
    Result.failure(e)
  }
}
