package com.example.newsapppp.presentation.screens.main

import androidx.navigation.NavDirections

sealed class MainAction {
    data class Navigate(val navigateTo: NavDirections) : MainAction()
    data class ShowMessage(val message: String?) : MainAction()
    data class ShowNetworkDialog(val message: Int) : MainAction()
}
