package com.example.newsapppp.data.articles.remote.interceptor

import com.example.newsapppp.R
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * This class represents an interceptor used to handle errors in HTTP requests.
 * It implements the Interceptor interface provided by the OkHttp library.
 * The RestErrorInterceptor intercepts the HTTP response and checks the response code.
 * If the code is one of the pre-defined error codes (400, 429, or 500), it emits an error message
 * using the ErrorsInterceptorContract.
 * @param errors An instance of ErrorsInterceptorContract used to emit error messages.
 */
class RestErrorInterceptor(private var errors: ErrorsInterceptorContract) : Interceptor {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    /**
     * This method intercepts the HTTP request and response.
     * If the response code is one of the pre-defined error codes, it emits an error message
     * using the ErrorsInterceptorContract.
     * @param chain An instance of Interceptor.Chain that represents the request and response chain.
     * @return The HTTP response from the server.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            400 -> emitError(R.string.often_request)
            401 -> emitError(R.string.api_error)
            429 -> emitError(R.string.many_request)
            500 -> emitError(R.string.server_error)
        }
        return response
    }

    private fun emitError(errorCodeResId: Int) = coroutineScope.launch {
        errors.emitError(errorCodeResId)
    }

    fun cancel() {
        coroutineScope.cancel()
    }
}
