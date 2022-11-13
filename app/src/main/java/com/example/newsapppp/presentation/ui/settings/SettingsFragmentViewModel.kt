package com.example.newsapppp.presentation.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.interactors.GetCountryFlagUseCase
import com.example.newsapppp.domain.interactors.GetSwitchPositionUseCase
import com.example.newsapppp.domain.interactors.SaveCountryFlagUseCase
import com.example.newsapppp.domain.interactors.SaveSwitchPositionUseCase
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(
    private val saveCountryFlagUseCase: SaveCountryFlagUseCase,
    private val getCountryFlagUseCase: GetCountryFlagUseCase,
    private val saveSwitchPositionUseCase: SaveSwitchPositionUseCase,
    private val getSwitchPositionUseCase: GetSwitchPositionUseCase
) : BaseViewModel<SettingsState>() {

    override val _state = MutableStateFlow<SettingsState>(SettingsState.SwitchPosition)
    override val state: StateFlow<SettingsState> = _state

    fun saveCountryFlag(value: String) = viewModelScope.launch {
        saveCountryFlagUseCase(value)
    }

    fun getCountryFlag(): String {
        return getCountryFlagUseCase(Unit)
    }

    fun getSwitchPosition() = viewModelScope.launch {
        val switchPosition = getSwitchPositionUseCase(Unit)
        if (switchPosition) {
            _state.emit(SettingsState.SwitchPosition)
        } else {
            _state.emit(SettingsState.UnSwitchPosition)
        }
    }

    fun saveChangeNightMode(enabled: Boolean) = viewModelScope.launch {
        if (enabled) {
            saveNightModeState(enabled = false, state = AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            saveNightModeState(enabled = true, state = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    private suspend fun saveNightModeState(enabled: Boolean, state: Int) {
        saveSwitchPositionUseCase(enabled)
        AppCompatDelegate.setDefaultNightMode(state)
    }
}
