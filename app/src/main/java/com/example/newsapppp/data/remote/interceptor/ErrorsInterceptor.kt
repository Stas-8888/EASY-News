package com.example.newsapppp.data.remote.interceptor

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ErrorsInterceptor @Inject constructor() : ErrorsInterceptorContract {
    val flow = MutableStateFlow(0)

    override fun errorsInterceptor(): StateFlow<Int> {
        return flow
    }

    override suspend fun emitError(error: Int){
        flow.emit(error)
    }
}
