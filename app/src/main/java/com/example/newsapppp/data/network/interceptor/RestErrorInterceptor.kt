package com.example.newsapppp.data.network.interceptor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RestErrorInterceptor(
    private var errors: ErrorsInterceptorContract
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            200 -> {
                CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
                    errors.emitError("Data, Success received")
                }
            }
            400 -> {
                CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
                    errors.emitError("The request was unacceptable, often due to a missing or misconfigured parameter.")
                }
            }
            429 -> {
                CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
                    errors.emitError(" You made too many requests within a window of time and have been rate limited. Back off for a while.")
                }
            }
            500 -> {
                CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
                    errors.emitError("Server Error")
                }
            }
        }
        return response
    }
}

