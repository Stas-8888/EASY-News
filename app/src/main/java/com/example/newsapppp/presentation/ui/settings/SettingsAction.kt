package com.example.newsapppp.presentation.ui.settings

import com.example.newsapppp.core.mvvm.Action

sealed class SettingsAction : Action {

    data class Navigate(val navigateTo: Int) : SettingsAction()

//    data class NavigateDirections(val navigateToSkip: NavDirections) : SignInAction()

}
