package com.example.newsapppp.presentation.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import com.example.newsapppp.R
import com.example.newsapppp.presentation.utils.extensions.launchCoroutine
import com.example.newsapppp.domain.interactors.preference.GetCountryFlagUseCase
import com.example.newsapppp.domain.interactors.preference.GetSwitchPositionUseCase
import com.example.newsapppp.domain.interactors.preference.SaveCountryFlagUseCase
import com.example.newsapppp.domain.interactors.preference.SaveSwitchPositionUseCase
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(
    private val saveCountryFlagUseCase: SaveCountryFlagUseCase,
    private val getCountryFlagUseCase: GetCountryFlagUseCase,
    private val saveSwitchPositionUseCase: SaveSwitchPositionUseCase,
    private val getSwitchPositionUseCase: GetSwitchPositionUseCase,
    private var firebaseAuth: FirebaseAuth
) : BaseViewModel<SettingsState>() {

    override val _state = MutableStateFlow<SettingsState>(SettingsState.IsSwitch(true))
    override val state: StateFlow<SettingsState> = _state

    fun saveCountryFlag(value: String) = launchCoroutine {
        saveCountryFlagUseCase(value)
    }

    fun getCountryFlag(): String {
        return getCountryFlagUseCase(Unit)
    }

    fun saveChangeNightMode(enabled: Boolean) = launchCoroutine {
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

    fun isSwitchDayNight() = launchCoroutine {
        if (getSwitchPositionUseCase(Unit)) {
            emitState(SettingsState.IsSwitch(false))
        } else {
            emitState(SettingsState.IsSwitch(true))
        }
    }

    fun getCurrentEmail() = launchCoroutine {
        firebaseAuth = FirebaseAuth.getInstance()
        val email = firebaseAuth.currentUser?.email
        emitState(SettingsState.GetCurrentEmail(email))
    }

    fun checkAccount() = launchCoroutine {
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            emitState(SettingsState.Account(
                    "Successful Sign Out",
                    true
                ) { firebaseAuth.signOut() })
        } else {
            emitState(SettingsState.Account2(R.id.loginFragment))
        }
    }
}

