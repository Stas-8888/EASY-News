package com.example.newsapppp.presentation.ui.settings

import android.view.MenuItem
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

private const val USA = "us"
private const val GERMANY = "de"
private const val RUSSIA = "ru"
private const val EGYPT = "eg"

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(
    private val saveCountryFlag: SaveCountryFlagUseCase,
    private val saveSwitchPosition: SaveSwitchPositionUseCase,
    private var firebaseAuth: FirebaseAuth,
    getCountryFlag: GetCountryFlagUseCase,
    getThemes: GetSwitchPositionUseCase
) : BaseViewModel<SettingsState, SettingsAction>() {

    override val _state = MutableStateFlow<SettingsState>(
        SettingsState.SetupUi(
            theme = !getThemes(Unit),
            email = firebaseAuth.currentUser?.email,
            flag = when (getCountryFlag(Unit)) {
                USA -> R.drawable.usa
                GERMANY -> R.drawable.germany
                RUSSIA -> R.drawable.russia
                EGYPT -> R.drawable.egypt
                else -> R.drawable.usa
            }
        )
    )
    override val state: StateFlow<SettingsState> = _state

    override val _shared = MutableSharedFlow<SettingsAction>()
    override val shared = _shared.asSharedFlow()

    fun onSwitchDayNightClicked(enabled: Boolean) = launchCoroutine {
        if (enabled) {
            saveSwitchPosition(false)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            saveSwitchPosition(true)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun onAccountClicked() = launchCoroutine {
        if (firebaseAuth.currentUser != null) {
            emitShared(SettingsAction.Account(
                "Do you, want to sign out?",
                true
            ) { firebaseAuth.signOut() })
        } else {
            emitShared(SettingsAction.Navigate(SettingsFragmentDirections.actionSettingsFragmentToSignInFragment()))
        }
    }

    fun setupPopupMenu(item: MenuItem) = launchCoroutine {
        when (item.itemId) {
            R.id.us -> {
                saveCountryFlag(USA)
                emit(SettingsState.SetCurrentCountry(R.drawable.usa))
                emitShared((SettingsAction.Message(R.string.American_News)))
            }
            R.id.ru -> {
                saveCountryFlag(RUSSIA)
                emit(SettingsState.SetCurrentCountry(R.drawable.russia))
                emitShared(SettingsAction.Message(R.string.Russia_News))
            }
            R.id.germany -> {
                saveCountryFlag(GERMANY)
                emit(SettingsState.SetCurrentCountry(R.drawable.germany))
                emitShared(SettingsAction.Message(R.string.Germany_News))
            }
            R.id.egipt -> {
                saveCountryFlag(EGYPT)
                emit(SettingsState.SetCurrentCountry(R.drawable.egypt))
                emitShared(SettingsAction.Message(R.string.Egypt_News))
            }
        }
    }
}

