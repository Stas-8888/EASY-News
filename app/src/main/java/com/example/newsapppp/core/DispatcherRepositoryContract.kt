package com.example.newsapppp.core

import kotlinx.coroutines.CoroutineScope

interface DispatcherRepositoryContract {

    suspend fun <T> io(block: suspend CoroutineScope.() -> T): T
}
