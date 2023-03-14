package com.example.newsapppp.presentation.screens.authentication.signin

import androidx.navigation.NavDirections

sealed class SignInAction {
    data class Navigate(val navigateTo: NavDirections) : SignInAction()
    data class ShowMessage(val message: Int) : SignInAction()
    data class ShowNetworkDialog(val message: Int) : SignInAction()
}
