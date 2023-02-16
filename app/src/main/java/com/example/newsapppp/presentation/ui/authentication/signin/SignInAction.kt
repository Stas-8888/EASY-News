package com.example.newsapppp.presentation.ui.authentication.signin

import androidx.navigation.NavDirections

sealed class SignInAction {

    data class Navigate(val navigateTo: NavDirections) : SignInAction()
}
