package com.example.newsapppp.presentation.ui.settings

import androidx.navigation.NavDirections

sealed class SettingsAction {
    data class Navigate(val navigateTo: NavDirections) : SettingsAction()
    data class Message(val message: Int) : SettingsAction()
    data class Account(
        val message: String,
        val isError: Boolean = false,
        val action: () -> Unit = {}
    ) : SettingsAction()

}
