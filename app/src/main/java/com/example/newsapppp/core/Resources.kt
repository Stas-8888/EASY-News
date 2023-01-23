package com.example.newsapppp.core

sealed class Resources<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T> : Resources<T>()

    class Success<T>(data: T) : Resources<T>()

    class Failure<T>(errorMessage: String?) : Resources<T>()
}
