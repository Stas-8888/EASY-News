package com.example.newsapppp.presentation.ui.authentication.forgotPassword

sealed class ForgotPasswordAction {
    data class Message(val message: String?) : ForgotPasswordAction()
}
