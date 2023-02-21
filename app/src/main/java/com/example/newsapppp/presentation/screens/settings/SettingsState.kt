package com.example.newsapppp.presentation.screens.settings

sealed class SettingsState {
    data class SetCurrentCountry(val countryFlag: Int) : SettingsState()
    data class SetupUi(val theme: Boolean, val email: String?, val flag: Int) : SettingsState()
}
