package app.s2c.core.base.util

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Iterable<T>.parallelForEach(
    concurrency: Int = DEFAULT_CONCURRENCY,
    block: suspend (value: T) -> Unit,
) {
    asFlow().flatMapMerge(concurrency = concurrency) { item ->
        flow {
            block(item)
            emit(Unit)
        }
    }.collect()
}
