package com.example.newsapppp.presentation.ui.authentication.signin

sealed class SignInState<out T> {
    object Loading : SignInState<Nothing>()
    data class Success<out T>(val data: T) : SignInState<T>()
    data class Failure(val error: String?) : SignInState<Nothing>()

    data class Navigate(
        val navigateTo: Int,
    ) : SignInState<Nothing>()

    data class NavigateSkip(
        val navigateToSkip: Int,
    ) : SignInState<Nothing>()

    data class NavigateForgotPassword(
        val navigateForgotPassword: Int,
    ) : SignInState<Nothing>()

    data class NavigateSignUp(
        val navigateSignUp: Int,
    ) : SignInState<Nothing>()

    data class CheckEmail(val data: String) : SignInState<Nothing>()
    data class CheckPassword(val data: String) : SignInState<Nothing>()
    data class CheckState(
        val name: String,
        val email: String,
        val password: String,
        val repeatPassword: String
    ) : SignInState<Nothing>()
}
