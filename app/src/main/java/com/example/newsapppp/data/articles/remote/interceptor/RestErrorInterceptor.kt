package com.example.newsapppp.data.articles.remote.interceptor

import com.example.newsapppp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
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
            400 -> {
                CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
                    errors.emitError(R.string.often_request)
                }
            }
            429 -> {
                CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
                    errors.emitError(R.string.many_request)
                }
            }
            500 -> {
                CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
                    errors.emitError(R.string.server_error)
                }
            }
        }
        return response
    }
}
