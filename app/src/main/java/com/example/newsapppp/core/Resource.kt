package com.example.newsapppp.core

import okhttp3.ResponseBody

// To handle all api response
sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(val errorCode: Int?, val errorBody: ResponseBody?) : Resource<Nothing>()
}
