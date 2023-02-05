package com.example.newsapppp.core.interceptor

import javax.inject.Inject

class ErrorsInterceptor @Inject constructor() : ErrorsInterceptorContract {

    override fun code200(): String {
        return "Data, Success received"
    }
}
