package com.example.newsapppp.presentation.ui.authentication.signin

import androidx.navigation.NavDirections
import com.example.newsapppp.core.mvvm.Action

sealed class SignInAction : Action {

    data class Navigate(val navigateTo: Int) : SignInAction()

    data class NavigateSkip(val navigateToSkip: NavDirections) : SignInAction()

    data class NavigateForgotPassword(
        val navigateForgotPassword: Int,
    ) : SignInAction()

    data class NavigateSignUp(
        val navigateSignUp: NavDirections,
    ) : SignInAction()

}
