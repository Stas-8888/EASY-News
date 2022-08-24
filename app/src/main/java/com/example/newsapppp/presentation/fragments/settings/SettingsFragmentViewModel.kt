package com.example.newsapppp.presentation.fragments.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(
    private val saveCountryFlagUseCase: SaveCountryFlagUseCase,
    private val getCountryFlagUseCase: GetCountryFlagUseCase,
    private val saveSwitchPositionUseCase: SaveSwitchPositionUseCase,
    private val getSwitchPositionUseCase: GetSwitchPositionUseCase
) : ViewModel() {

    fun saveCountryFlag(value: String) = viewModelScope.launch {
        saveCountryFlagUseCase.saveCountryFlag(value)
    }

    fun getCountryFlag(): String {
        return getCountryFlagUseCase.getCountryFlag()
    }

    fun saveSwitchPosition(value: Boolean) = viewModelScope.launch {
        saveSwitchPositionUseCase.saveSwitchPosition(value)
    }

    fun getSwitchPosition(): Boolean {
        return getSwitchPositionUseCase.getSwitchPosition()
    }
}
