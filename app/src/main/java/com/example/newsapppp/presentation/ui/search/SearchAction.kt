package com.example.newsapppp.presentation.ui.search

import com.example.newsapppp.core.mvvm.Action

sealed class SearchAction : Action {

    data class Navigate(val navigateTo: Int) : SearchAction()

//    data class NavigateDirections(val navigateToSkip: NavDirections) : SignInAction()

}
