package com.example.newsapppp.presentation.ui.splash

import com.example.newsapppp.core.mvvm.Action

sealed class SplashAction : Action {

    data class Navigate(val navigateTo: Int) : SplashAction()

//    data class NavigateDirections(val navigateToSkip: NavDirections) : SignInAction()

}
