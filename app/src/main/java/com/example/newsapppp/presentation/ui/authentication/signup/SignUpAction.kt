package com.example.newsapppp.presentation.ui.authentication.signup

import androidx.navigation.NavDirections

sealed class SignUpAction {
    data class Navigate(val navigateTo: NavDirections) : SignUpAction()
    data class Message(val message: String?) : SignUpAction()
}
