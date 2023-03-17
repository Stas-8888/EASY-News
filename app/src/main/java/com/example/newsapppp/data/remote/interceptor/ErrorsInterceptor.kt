package com.example.newsapppp.data.remote.interceptor

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class ErrorsInterceptor @Inject constructor() : ErrorsInterceptorContract {
    val flow = MutableSharedFlow<Int>()

    override fun errorsInterceptor(): SharedFlow<Int> {
        return flow
    }

    override suspend fun emitError(error: Int){
        flow.emit(error)
    }
}
