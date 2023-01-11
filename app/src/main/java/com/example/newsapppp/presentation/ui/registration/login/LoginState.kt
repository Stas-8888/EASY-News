package com.example.newsapppp.presentation.ui.registration.login

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val navigateTo: Unit, val success: String) : LoginState()
    data class Error(val message: String) : LoginState()
    data class CheckEmail(val data: String) : LoginState()
    data class CheckPassword(val data: String) : LoginState()
}
