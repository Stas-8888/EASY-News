package com.example.newsapppp.presentation.ui.registration.login

sealed class LoginState {
    object Loading : LoginState()
    data class Success(val navigateTo: Int, val successMessage: Int) : LoginState()
    data class Error(val message: Int) : LoginState()
    data class CheckEmail(val data: String) : LoginState()
    data class CheckPassword(val data: String) : LoginState()
}
