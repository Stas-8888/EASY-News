package com.example.newsapppp.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface Dispatchers {

    suspend fun <T> io(block: suspend CoroutineScope.() -> T): T
}

class Base @Inject constructor() : com.example.newsapppp.core.Dispatchers {

    override suspend fun <T> io(block: suspend CoroutineScope.() -> T): T =
        withContext(Dispatchers.IO, block)
}