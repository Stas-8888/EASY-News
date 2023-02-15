package com.example.newsapppp.presentation.ui.authentication.forgotPassword

import com.example.newsapppp.core.mvvm.Action

sealed class ForgotPasswordAction : Action {

    data class Navigate(val navigateTo: Int) : ForgotPasswordAction()

//    data class NavigateDirections(val navigateToSkip: NavDirections) : SignInAction()

}
