package com.example.newsapppp.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DispatchersList {

//    fun withContextIO(): CoroutineDispatcher

    suspend fun <T> withContextIO(block: suspend CoroutineScope.() -> T): T
}

class Base @Inject constructor() : DispatchersList {
//    override fun withContextIO(): CoroutineDispatcher = Dispatchers.IO

    override suspend fun <T> withContextIO(block: suspend CoroutineScope.() -> T): T =
        withContext(Dispatchers.IO, block)

}
