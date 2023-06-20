package com.example.newsapppp.data.interceptor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * This class implements the [ErrorsInterceptorRepository] interface to provide an
 * implementation for error handling in a flow.
 *
 * @property interceptorFlow The [MutableSharedFlow] used to emit error codes.
 * @constructor Creates an instance of [ErrorsInterceptor].
 */
class ErrorsInterceptor @Inject constructor() : ErrorsInterceptorRepository {
    private val interceptorFlow = MutableSharedFlow<Int>()

    /**
     * @return The [SharedFlow] used to emit error codes.
     */
    override fun errorsInterceptor(): SharedFlow<Int> = interceptorFlow

    /**
     * @param error The error code to be emitted to the [interceptorFlow].
     */
    override suspend fun emitError(error: Int) = withContext(Dispatchers.IO) {
        interceptorFlow.emit(error)
    }
}
