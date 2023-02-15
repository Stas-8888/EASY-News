package com.example.newsapppp.presentation.ui.authentication.signup

import com.example.newsapppp.core.mvvm.Action

sealed class SignUpAction : Action {

    data class Navigate(val navigateTo: Int) : SignUpAction()

//    data class NavigateDirections(val navigateToSkip: NavDirections) : SignInAction()

}
