package com.example.newsapppp.presentation.ui.authentication.signin

sealed class SignInState<out T> {
    data class Loading(val loading: Boolean) : SignInState<Nothing>()
    data class CheckEmail(val data: String) : SignInState<Nothing>()
    data class CheckPassword(val data: String) : SignInState<Nothing>()
}
