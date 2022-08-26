package com.example.newsapppp.presentation.fragments.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.synthetic.main.fragment_settings.*
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

    private fun saveSwitchPosition(value: Boolean) = viewModelScope.launch {
        saveSwitchPositionUseCase.saveSwitchPosition(value)
    }

    fun getSwitchPosition(): Boolean {
        return getSwitchPositionUseCase.getSwitchPosition()
    }

    fun isNightMode(){
        saveSwitchPosition(false)
        AppCompatDelegate
            .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    fun isDayMode(){
        saveSwitchPosition(true)
        AppCompatDelegate
            .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}
