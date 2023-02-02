package com.example.newsapppp.presentation.ui.authentication.forgotPassword

sealed class ForgotPasswordState<out T> {
    object Loading : ForgotPasswordState<Nothing>()
    data class Success<out T>(val data: T) : ForgotPasswordState<T>()
    data class Failure(val error: String?) : ForgotPasswordState<Nothing>()
    data class Navigate(val navigateTo: Int) : ForgotPasswordState<Nothing>()
    data class CheckEmail(val data: String) : ForgotPasswordState<Nothing>()
}
