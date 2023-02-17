package com.example.newsapppp.presentation.ui.main

import androidx.navigation.NavDirections

sealed class MainAction {
    data class Navigate(val navigateTo: NavDirections) : MainAction()
    data class Message(val message: String?) : MainAction()
}
