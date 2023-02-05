package com.example.newsapppp.core.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RestErrorInterceptor: Interceptor {
    private var errors: ErrorsInterceptorContract? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            200 ->  errors?.code200()
        }
        return response
    }
}

