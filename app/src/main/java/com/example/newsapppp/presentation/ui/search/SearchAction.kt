package com.example.newsapppp.presentation.ui.search

import androidx.navigation.NavDirections

sealed class SearchAction {
    data class Navigate(val navigateTo: NavDirections) : SearchAction()
    data class Message(val message: Int) : SearchAction()
}
