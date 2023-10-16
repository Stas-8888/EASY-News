package com.example.newsapppp.common.dispatcher

import kotlinx.coroutines.CoroutineScope

interface DispatcherHelper {

    suspend fun <T> io(block: suspend CoroutineScope.() -> T): T
}
