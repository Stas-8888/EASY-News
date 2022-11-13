package com.example.newsapppp.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface DispatchersList {

    fun io(): CoroutineDispatcher

    class Base @Inject constructor() : DispatchersList {
        override fun io(): CoroutineDispatcher = Dispatchers.IO
    }
}
