package com.example.newsapppp.presentation.ui.news

import com.example.newsapppp.core.mvvm.Action

sealed class NewsAction : Action {

    data class Navigate(val navigateTo: Int) : NewsAction()

//    data class NavigateDirections(val navigateToSkip: NavDirections) : SignInAction()

}
