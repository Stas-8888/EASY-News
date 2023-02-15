package com.example.newsapppp.presentation.ui.save

import com.example.newsapppp.core.mvvm.Action

sealed class SaveAction : Action {

    data class Navigate(val navigateTo: Int) : SaveAction()

//    data class NavigateDirections(val navigateToSkip: NavDirections) : SignInAction()

}
