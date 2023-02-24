package com.example.newsapppp.data.remote.interceptor

import kotlinx.coroutines.flow.Flow

interface ErrorsInterceptorContract {

    fun errorsInterceptor(): Flow<String>

    suspend fun emitError(error: String)
}
