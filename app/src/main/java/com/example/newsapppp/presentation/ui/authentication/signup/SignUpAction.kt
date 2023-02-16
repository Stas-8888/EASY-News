package com.example.newsapppp.presentation.ui.authentication.signup

sealed class SignUpAction {

    data class Navigate(val navigateTo: Int) : SignUpAction()
}
