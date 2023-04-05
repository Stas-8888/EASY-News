package com.example.newsapppp.data.articles.remote.interceptor

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

/**
 * This class implements the [ErrorsInterceptorContract] interface to provide an
 * implementation for error handling in a flow.
 *
 * @property flow The [MutableSharedFlow] used to emit error codes.
 * @constructor Creates an instance of [ErrorsInterceptor].
 */
class ErrorsInterceptor @Inject constructor() : ErrorsInterceptorContract {
    val flow = MutableSharedFlow<Int>()

    /**
     * @return The [SharedFlow] used to emit error codes.
     */
    override fun errorsInterceptor(): SharedFlow<Int> {
        return flow
    }

    /**
     * @param error The error code to be emitted to the [flow].
     */
    override suspend fun emitError(error: Int) {
        flow.emit(error)
    }
}
