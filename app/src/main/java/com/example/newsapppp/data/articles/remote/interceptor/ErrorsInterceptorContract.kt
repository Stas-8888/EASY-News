package com.example.newsapppp.data.articles.remote.interceptor

import kotlinx.coroutines.flow.SharedFlow

interface ErrorsInterceptorContract {

    fun errorsInterceptor(): SharedFlow<Int>

    suspend fun emitError(error: Int)
}