package com.example.newsapppp.presentation.ui.main

import com.example.newsapppp.core.mvvm.Action

sealed class MainAction : Action {

    data class Navigate(val navigateTo: Int) : MainAction()

//    data class NavigateDirections(val navigateToSkip: NavDirections) : SignInAction()

}
