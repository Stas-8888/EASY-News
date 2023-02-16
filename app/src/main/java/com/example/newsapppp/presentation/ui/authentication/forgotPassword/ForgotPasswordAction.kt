package com.example.newsapppp.presentation.ui.authentication.forgotPassword

sealed class ForgotPasswordAction {

    data class Navigate(val navigateTo: Int) : ForgotPasswordAction()
}
