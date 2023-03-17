package com.example.newsapppp.data.remote.interceptor

import com.example.newsapppp.R
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

