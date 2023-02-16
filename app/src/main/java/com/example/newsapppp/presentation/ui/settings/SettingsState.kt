package com.example.newsapppp.presentation.ui.settings

sealed class SettingsState {

    data class Account(
        val message: String,
        val isError: Boolean = false,
        val action: () -> Unit = {}
    ) : SettingsState()

    data class SaveCurrentCountry(
        val countryFlag: Int,
        val countryName: Int
    ) : SettingsState()

    data class Navigate(val navigation: Int) : SettingsState()

    data class SetupUi(val theme: Boolean, val email: String?, val flag: Int) : SettingsState()
}
