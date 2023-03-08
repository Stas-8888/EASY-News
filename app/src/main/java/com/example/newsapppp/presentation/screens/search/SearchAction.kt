package com.example.newsapppp.presentation.screens.search

import androidx.navigation.NavDirections

sealed class SearchAction {
    data class Navigate(val navigateTo: NavDirections) : SearchAction()
    data class ShowMessage(val message: Int) : SearchAction()
    data class ShowNetworkDialog(val message: Int) : SearchAction()
}
