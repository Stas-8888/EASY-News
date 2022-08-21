package com.example.newsapppp.domain.model

sealed class UseCaseResponse<T> {

    class Error<T> : UseCaseResponse<T>()

    data class Success<T>(val data: T) : UseCaseResponse<T>()

}
