package com.example.newsapppp.core.dispatcher

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * This class provides coroutine dispatchers for different contexts,
 * specifically the IO dispatcher through the [io] function.
 */
class DispatcherRepository @Inject constructor() : DispatcherRepositoryContract {

    /**
     * Executes a [block] in the IO dispatcher and suspends the current coroutine
     * until the block completes.
     *
     * @param block The coroutine block to execute in the IO dispatcher.
     * @return The result of the coroutine block.
     */
    override suspend fun <T> io(block: suspend CoroutineScope.() -> T): T =
        withContext(Dispatchers.IO, block)
}
