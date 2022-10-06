package com.example.newsapppp.presentation.fragments.settings

import com.example.newsapppp.presentation.fragments.base.State

sealed class SettingsState: State {

    object SwitchPosition : SettingsState()

    object UnSwitchPosition : SettingsState()
}
