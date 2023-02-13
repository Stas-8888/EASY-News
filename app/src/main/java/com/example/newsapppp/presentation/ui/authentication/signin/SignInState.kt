package com.example.newsapppp.presentation.ui.authentication.signin

import androidx.navigation.NavDirections

sealed class SignInState<out T> {
    object Loading : SignInState<Nothing>()
    data class Success<out T>(val data: T) : SignInState<T>()
    data class Failure(val error: String?) : SignInState<Nothing>()

    data class Navigate(
        val navigateTo: Int,
    ) : SignInState<Nothing>()

    data class NavigateSkip(
        val navigateToSkip: NavDirections,
    ) : SignInState<Nothing>()

    data class NavigateForgotPassword(
        val navigateForgotPassword: Int,
    ) : SignInState<Nothing>()

    data class NavigateSignUp(
        val navigateSignUp: NavDirections,
    ) : SignInState<Nothing>()

    data class CheckEmail(val data: String) : SignInState<Nothing>()
    data class CheckPassword(val data: String) : SignInState<Nothing>()
}
