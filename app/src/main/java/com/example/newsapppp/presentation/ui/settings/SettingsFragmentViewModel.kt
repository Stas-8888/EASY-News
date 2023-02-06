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
    private val saveCountryFlag: SaveCountryFlagUseCase,
    private val getCountryFlag: GetCountryFlagUseCase,
    private val saveSwitchPosition: SaveSwitchPositionUseCase,
    private var firebaseAuth: FirebaseAuth,
    getThemes: GetSwitchPositionUseCase
    ) : BaseViewModel<SettingsState>() {

    override val _state = MutableStateFlow<SettingsState>(
        SettingsState.SetupUi(
            email = firebaseAuth.currentUser?.email,
            flag = setupCountryFlag(),
            theme = !getThemes(Unit)
        )
    )
    override val state: StateFlow<SettingsState> = _state

    fun onSwitchDayNightClicked(enabled: Boolean) = launchCoroutine {
        if (enabled) {
            saveSwitchPosition(false)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            saveSwitchPosition(true)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setupCountryFlag(): Int {
        return when (getCountryFlag(Unit)) {
            USA -> R.drawable.usa
            GERMANY -> R.drawable.germany
            RUSSIA -> R.drawable.russia
            EGYPT -> R.drawable.egypt
            else -> {
                0
            }
        }
    }

    fun checkAccount() = launchCoroutine {
        if (firebaseAuth.currentUser != null) {
            emit(SettingsState.Account(
                "Do you, want to sign out?",
                true
            ) { firebaseAuth.signOut() })
        } else {
            emit(SettingsState.Navigate(R.id.loginFragment))
        }
    }

    fun saveUsaCountry() = launchCoroutine {
        saveCountryFlag(USA)
        emit(SettingsState.SaveCurrentCountry(R.drawable.usa, R.string.American_News))
    }

    fun saveRussiaCountry() = launchCoroutine {
        saveCountryFlag(RUSSIA)
        emit(SettingsState.SaveCurrentCountry(R.drawable.russia, R.string.Russia_News))
    }

    fun saveGermanyCountry() = launchCoroutine {
        saveCountryFlag(GERMANY)
        emit(
            SettingsState.SaveCurrentCountry(R.drawable.germany, R.string.Germany_News)
        )
    }

    fun saveEgyptCountry() = launchCoroutine {
        saveCountryFlag(EGYPT)
        emit(SettingsState.SaveCurrentCountry(R.drawable.egypt, R.string.Egypt_News))
    }
}

