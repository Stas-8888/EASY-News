package com.example.newsapppp.presentation.screens.main

import androidx.navigation.NavDirections

sealed class MainAction {
    data class OnClicked(val navigateTo: NavDirections) : MainAction()
    data class ShowError(val message: Int) : MainAction()
    data class ShowNetworkDialog(val message: Int) : MainAction()
}
