package com.example.newsapppp.presentation.ui.splash

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.interactors.GetSwitchPositionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashActivityViewModel @Inject constructor(
    private val getSwitchPositionUseCase: GetSwitchPositionUseCase
) : ViewModel() {

    private fun getSwitchPosition(): Boolean {
        return getSwitchPositionUseCase(Unit)
    }

    fun setupDayNightMode() = viewModelScope.launch {
        val nightMode = getSwitchPosition()
        if (nightMode) {
            AppCompatDelegate
                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        } else {
            AppCompatDelegate
                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}
