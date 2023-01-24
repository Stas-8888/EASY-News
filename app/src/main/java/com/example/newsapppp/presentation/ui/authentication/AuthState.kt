package com.example.newsapppp.presentation.ui.authentication

sealed class AuthState<out T> {
    object Loading : AuthState<Nothing>()
    data class Success<out T>(val data: T) : AuthState<T>()
    data class Failure(val error: String?) : AuthState<Nothing>()
    data class Navigate(val navigateTo: Int) : AuthState<Nothing>()
    data class CheckEmail(val data: String) : AuthState<Nothing>()
    data class CheckPassword(val data: String) : AuthState<Nothing>()
    data class CheckState(
        val name: String,
        val email: String,
        val password: String,
        val repeatPassword: String
    ) : AuthState<Nothing>()
}
