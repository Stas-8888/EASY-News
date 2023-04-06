package com.example.newsapppp.presentation.screens.settings

import androidx.navigation.NavDirections

sealed class SettingsAction {
    data class Navigate(val navigateTo: NavDirections) : SettingsAction()
    data class ShowMessage(val message: Int) : SettingsAction()
    data class ShowConfirmation(
        val message: Int,
        val isError: Boolean = false,
        val isPositiveAction: () -> Unit = {}
    ) : SettingsAction()

}
