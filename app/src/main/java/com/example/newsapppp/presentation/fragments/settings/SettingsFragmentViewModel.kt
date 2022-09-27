package com.example.newsapppp.presentation.fragments.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.usecase.GetCountryFlagUseCase
import com.example.newsapppp.domain.usecase.GetSwitchPositionUseCase
import com.example.newsapppp.domain.usecase.SaveCountryFlagUseCase
import com.example.newsapppp.domain.usecase.SaveSwitchPositionUseCase
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
) : ViewModel() {

    private val _state = MutableStateFlow<SettingsState>(SettingsState.SwitchPosition)
    val state: StateFlow<SettingsState> = _state

    fun saveCountryFlag(value: String) = viewModelScope.launch {
        saveCountryFlagUseCase.saveCountryFlag(value)
    }

    fun getCountryFlag(): String {
        return getCountryFlagUseCase.getCountryFlag()
    }

    private fun saveSwitchPosition(value: Boolean) = viewModelScope.launch {
        saveSwitchPositionUseCase.saveSwitchPosition(value)
    }

    fun getSwitchPosition() = viewModelScope.launch {
        val switchPosition = getSwitchPositionUseCase.getSwitchPosition()
        if (switchPosition) {
            _state.emit(SettingsState.SwitchPosition)
        } else {
            _state.emit(SettingsState.UnSwitchPosition)
        }
    }

    fun saveChangeNightMode(value: Boolean) {
        if (value) {
            checkNightModeState(value = false, state = AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            checkNightModeState(value = true, state = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    private fun checkNightModeState(value: Boolean, state: Int) {
        saveSwitchPosition(value)
        AppCompatDelegate
            .setDefaultNightMode(state)
    }
}
