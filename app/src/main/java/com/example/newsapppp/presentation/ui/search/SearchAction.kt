package com.example.newsapppp.presentation.ui.search

import androidx.navigation.NavDirections

sealed class SearchAction {
    data class Navigate(val navigateTo: NavDirections) : SearchAction()
    data class ShowMessage(val message: Int) : SearchAction()
    data class ShowNetworkConnections(val message: Int) : SearchAction()
}
