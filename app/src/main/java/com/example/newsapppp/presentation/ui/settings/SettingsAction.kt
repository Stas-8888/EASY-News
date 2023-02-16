package com.example.newsapppp.presentation.ui.settings

sealed class SettingsAction {

    data class Navigate(val navigateTo: Int) : SettingsAction()
}
