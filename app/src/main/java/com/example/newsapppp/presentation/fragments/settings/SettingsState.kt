package com.example.newsapppp.presentation.fragments.settings

sealed class SettingsState {

    object SwitchPosition : SettingsState()

    object UnSwitchPosition : SettingsState()
}
