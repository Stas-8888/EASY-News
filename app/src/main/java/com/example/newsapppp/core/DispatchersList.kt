package com.example.newsapppp.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DispatchersList {

    suspend fun <T> iO(block: suspend CoroutineScope.() -> T): T
}

class Base @Inject constructor() : DispatchersList {

    override suspend fun <T> iO(block: suspend CoroutineScope.() -> T): T =
        withContext(Dispatchers.IO, block)
}
