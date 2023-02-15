package com.example.newsapppp.presentation.ui.authentication.signin

import com.example.newsapppp.core.mvvm.Action

sealed class SignInAction : Action {

    data class Navigate(val navigateTo: Int) : SignInAction()

//    data class NavigateDirections(val navigateToSkip: NavDirections) : SignInAction()

}
