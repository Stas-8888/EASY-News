package com.example.newsapppp.core.dispatcher

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DispatcherRepository @Inject constructor() : DispatcherRepositoryContract {

    override suspend fun <T> io(block: suspend CoroutineScope.() -> T): T =
        withContext(Dispatchers.IO, block)
}