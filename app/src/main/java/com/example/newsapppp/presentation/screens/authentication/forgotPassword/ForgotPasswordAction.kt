package com.example.newsapppp.presentation.screens.authentication.forgotPassword

sealed class ForgotPasswordAction {
    data class ShowMessage(val message: Int) : ForgotPasswordAction()
    data class ShowNetworkDialog(val message: Int) : ForgotPasswordAction()
}
