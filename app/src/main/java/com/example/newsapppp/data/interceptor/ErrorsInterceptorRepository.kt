package com.example.newsapppp.data.interceptor

import kotlinx.coroutines.flow.SharedFlow

interface ErrorsInterceptorRepository {

    fun errorsInterceptor(): SharedFlow<Int>

    suspend fun emitError(error: Int)
}
