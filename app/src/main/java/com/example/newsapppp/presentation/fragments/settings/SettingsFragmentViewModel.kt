package com.example.newsapppp.presentation.fragments.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.usecase.GetCountryFlagUseCase
import com.example.newsapppp.domain.usecase.GetSwitchPositionUseCase
import com.example.newsapppp.domain.usecase.SaveCountryFlagUseCase
import com.example.newsapppp.domain.usecase.SaveSwitchPositionUseCase
import com.example.newsapppp.presentation.fragments.base.BaseViewModel
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
        saveCountryFlagUseCase.saveCountryFlag(value)
    }

    fun getCountryFlag(): String {
        return getCountryFlagUseCase.getCountryFlag()
    }

    fun getSwitchPosition() = viewModelScope.launch {
        val switchPosition = getSwitchPositionUseCase.getSwitchPosition()
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
        saveSwitchPositionUseCase.saveSwitchPosition(enabled)
        AppCompatDelegate.setDefaultNightMode(state)
    }
}
