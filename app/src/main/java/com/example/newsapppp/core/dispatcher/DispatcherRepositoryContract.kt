package com.example.newsapppp.core.dispatcher

import kotlinx.coroutines.CoroutineScope

interface DispatcherRepositoryContract {

    suspend fun <T> io(block: suspend CoroutineScope.() -> T): T
}
