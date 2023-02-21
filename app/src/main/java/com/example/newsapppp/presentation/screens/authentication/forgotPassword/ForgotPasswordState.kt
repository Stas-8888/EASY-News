package com.example.newsapppp.presentation.screens.authentication.forgotPassword

sealed class ForgotPasswordState<out T> {

    object Loading : ForgotPasswordState<Nothing>()
    data class CheckEmail(val data: String) : ForgotPasswordState<Nothing>()
}
