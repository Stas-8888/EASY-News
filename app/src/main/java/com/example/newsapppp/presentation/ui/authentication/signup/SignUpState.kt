package com.example.newsapppp.presentation.ui.authentication.signup

sealed class SignUpState<out T> {
    object Loading : SignUpState<Nothing>()

    data class CheckState(
        val name: String,
        val email: String,
        val password: String,
        val repeatPassword: String
    ) : SignUpState<Nothing>()
}
