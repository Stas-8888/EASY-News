package com.example.newsapppp.data.remote.interceptor

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ErrorsInterceptor @Inject constructor() : ErrorsInterceptorContract {
    val flow = MutableStateFlow("")

    override fun errorsInterceptor(): StateFlow<String> {
        return flow
    }

    override suspend fun emitError(error: String){
        flow.emit(error)
    }
}
