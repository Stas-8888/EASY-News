package com.example.newsapppp.presentation.ui.settings

import com.example.newsapppp.core.State

sealed class SettingsState: State {

    object SwitchPosition : SettingsState()

    object UnSwitchPosition : SettingsState()
}
