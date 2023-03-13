package com.example.newsapppp.presentation.screens.settings

import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.sharedpreferences.GetCountryFlagUseCase
import com.example.newsapppp.domain.interactors.sharedpreferences.GetSwitchPositionUseCase
import com.example.newsapppp.domain.interactors.sharedpreferences.SaveCountryFlagUseCase
import com.example.newsapppp.domain.interactors.sharedpreferences.SaveSwitchPositionUseCase
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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
    private var getCountryFlag: GetCountryFlagUseCase,
    private var getThemes: GetSwitchPositionUseCase
) : BaseViewModel<SettingsState, SettingsAction>() {

    override val _state =
        MutableStateFlow<SettingsState>(SettingsState.SetCurrentCountry(R.drawable.usa))
    override val _action = MutableSharedFlow<SettingsAction>()

    // Update the day/night mode of the app and save the switch position
    fun onSwitchDayNightClicked(enabled: Boolean) = viewModelScope.launch {
        if (enabled) {
            saveSwitchPosition(false)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            saveSwitchPosition(true)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    // sets up the UI of the settings screen
    fun setupUi() {
        val ui = SettingsState.ShowUi(
            theme = !getThemes(Unit),
            email = firebaseAuth.currentUser?.email ?: "Email address",
            flag = when (getCountryFlag(Unit)) {
                USA -> R.drawable.usa
                GERMANY -> R.drawable.germany
                RUSSIA -> R.drawable.russia
                EGYPT -> R.drawable.egypt
                else -> R.drawable.usa
            }
        )
        emit(ui)
    }

    // handles the click on the Account
    fun onAccountClicked() = viewModelScope.launch {
        if (firebaseAuth.currentUser != null) {
            emitAction(SettingsAction.ShowAccount(
                "Do you, want to sign out?",
                true
            ) { firebaseAuth.signOut() })
        } else {
            val action =
                SettingsFragmentDirections.actionSettingsFragmentToAuthBottomSheetFragment()
            emitAction(SettingsAction.Navigate(action))
        }
    }

    // sets up the popup menu of the settings screen
    fun setupPopupMenu(item: MenuItem) = viewModelScope.launch {
        when (item.itemId) {
            R.id.us -> {
                saveCountryFlag(USA)
                emit(SettingsState.SetCurrentCountry(R.drawable.usa))
                emitAction((SettingsAction.ShowMessage(R.string.american_news)))
            }
            R.id.ru -> {
                saveCountryFlag(RUSSIA)
                emit(SettingsState.SetCurrentCountry(R.drawable.russia))
                emitAction(SettingsAction.ShowMessage(R.string.russia_news))
            }
            R.id.germany -> {
                saveCountryFlag(GERMANY)
                emit(SettingsState.SetCurrentCountry(R.drawable.germany))
                emitAction(SettingsAction.ShowMessage(R.string.germany_news))
            }
            R.id.egipt -> {
                saveCountryFlag(EGYPT)
                emit(SettingsState.SetCurrentCountry(R.drawable.egypt))
                emitAction(SettingsAction.ShowMessage(R.string.egypt_news))
            }
        }
    }

    // handles the click on the profile image options menu
    fun onProfileImageClicked(item: MenuItem, launchGallery: () -> Unit) {
        when (item.itemId) {
            R.id.galleryMenu -> launchGallery.invoke()
            R.id.cameraMenu -> {}
        }
    }
}

