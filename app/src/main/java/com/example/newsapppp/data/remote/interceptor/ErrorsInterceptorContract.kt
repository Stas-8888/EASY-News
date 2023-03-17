package com.example.newsapppp.data.remote.interceptor

import kotlinx.coroutines.flow.Flow

interface ErrorsInterceptorContract {

    fun errorsInterceptor(): Flow<Int>

    suspend fun emitError(error: Int)
}
