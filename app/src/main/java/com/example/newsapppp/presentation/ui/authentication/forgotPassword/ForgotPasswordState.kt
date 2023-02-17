package com.example.newsapppp.presentation.ui.authentication.forgotPassword

sealed class ForgotPasswordState<out T> {
    object Loading : ForgotPasswordState<Nothing>()
    data class Success<out T>(val data: T) : ForgotPasswordState<T>()
    data class CheckEmail(val data: String) : ForgotPasswordState<Nothing>()
}
