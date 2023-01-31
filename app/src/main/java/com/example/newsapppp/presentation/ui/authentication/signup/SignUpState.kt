package com.example.newsapppp.presentation.ui.authentication.signup

sealed class SignUpState<out T> {
    object Loading : SignUpState<Nothing>()
    data class Success<out T>(val data: T) : SignUpState<T>()
    data class Failure(val error: String?) : SignUpState<Nothing>()

    data class Navigate(
        val navigateTo: Int,
    ) : SignUpState<Nothing>()

    data class CheckState(
        val name: String,
        val email: String,
        val password: String,
        val repeatPassword: String
    ) : SignUpState<Nothing>()
}
