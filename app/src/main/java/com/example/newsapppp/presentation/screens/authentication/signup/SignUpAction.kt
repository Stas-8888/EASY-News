package com.example.newsapppp.presentation.screens.authentication.signup

import androidx.navigation.NavDirections

sealed class SignUpAction {
    data class ShowMessage(val message: Int) : SignUpAction()
    data class ShowNetworkDialog(val message: Int) : SignUpAction()
    data class Navigate(val navigateTo: NavDirections) : SignUpAction()
}
