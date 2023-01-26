package com.example.newsapppp.presentation.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.preference.GetCountryFlagUseCase
import com.example.newsapppp.domain.interactors.preference.GetSwitchPositionUseCase
import com.example.newsapppp.domain.interactors.preference.SaveCountryFlagUseCase
import com.example.newsapppp.domain.interactors.preference.SaveSwitchPositionUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

private const val USA = "us"
private const val GERMANY = "de"
private const val RUSSIA = "ru"
private const val EGYPT = "eg"

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(
    private val saveCountryFlagUseCase: SaveCountryFlagUseCase,
    private val getCountryFlagUseCase: GetCountryFlagUseCase,
    private val saveSwitchPositionUseCase: SaveSwitchPositionUseCase,
    private val getSwitchPositionUseCase: GetSwitchPositionUseCase,
    private var firebaseAuth: FirebaseAuth,
) : BaseViewModel<SettingsState>() {

    override val _state = MutableStateFlow<SettingsState>(SettingsState.IsSwitch(true))
    override val state: StateFlow<SettingsState> = _state

    private suspend fun saveCountryFlag(value: String) {
        saveCountryFlagUseCase(value)
    }

    private fun getCountryFlag(): String {
        return getCountryFlagUseCase(Unit)
    }

    fun saveDayNightState(enabled: Boolean) = launchCoroutine {
        if (enabled) {
            setDefaultNightMode(enabled = false, state = AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            setDefaultNightMode(enabled = true, state = AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private suspend fun setDefaultNightMode(enabled: Boolean, state: Int) {
        saveSwitchPositionUseCase(enabled)
        AppCompatDelegate.setDefaultNightMode(state)
    }

    fun onSwitchDayNightClick() = launchCoroutine {
        if (getSwitchPositionUseCase(Unit)) {
            emitState(SettingsState.IsSwitch(false))
        } else {
            emitState(SettingsState.IsSwitch(true))
        }
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

    fun setupCountryFlag() {
        when (getCountryFlag()) {
            USA -> emitState(SettingsState.SetupCountryFlag(R.drawable.usa))
            GERMANY -> emitState(SettingsState.SetupCountryFlag(R.drawable.germany))
            RUSSIA -> emitState(SettingsState.SetupCountryFlag(R.drawable.russia))
            EGYPT -> emitState(SettingsState.SetupCountryFlag(R.drawable.egypt))
        }
    }

    fun saveUsaCountry() = launchCoroutine {
        emitState(
            SettingsState.SaveCurrentCountry(
                countryFlag = saveCountryFlag(USA),
                imageResource = R.drawable.usa,
                countryName = R.string.American_News
            )
        )
    }

    fun saveRussiaCountry() = launchCoroutine {
        emitState(
            SettingsState.SaveCurrentCountry(
                countryFlag = saveCountryFlag(RUSSIA),
                imageResource = R.drawable.russia,
                countryName = R.string.Russia_News
            )
        )
    }

    fun saveGermanyCountry() = launchCoroutine {
        emitState(
            SettingsState.SaveCurrentCountry(
                countryFlag = saveCountryFlag(GERMANY),
                imageResource = R.drawable.germany,
                countryName = R.string.Germany_News
            )
        )
    }

    fun saveEgyptCountry() = launchCoroutine {
        emitState(
            SettingsState.SaveCurrentCountry(
                countryFlag = saveCountryFlag(EGYPT),
                imageResource = R.drawable.egypt,
                countryName = R.string.Egypt_News
            )
        )
    }
}

