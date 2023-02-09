package com.example.newsapppp.data.network.interceptor

import kotlinx.coroutines.flow.Flow

interface ErrorsInterceptorContract {

    fun errorsInterceptor(): Flow<String>

    suspend fun emitError(error: String)
}
