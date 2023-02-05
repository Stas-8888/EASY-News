package com.example.newsapppp.core.interceptor

import kotlinx.coroutines.flow.Flow

interface ErrorsInterceptorContract {

    fun code200(): Flow<String>

    suspend fun emitError(error: String)
}
