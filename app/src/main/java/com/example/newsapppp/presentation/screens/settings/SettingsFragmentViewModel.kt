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

    // sets up the UI of the screen
    fun updateUi() {
        val theme = getThemes(Unit).not()
        val email = firebaseAuth.currentUser?.email
        val flagDrawableRes = getCountryFlagDrawableRes(getCountryFlag(Unit))
        emit(SettingsState.ShowUi(theme = theme, email = email, flag = flagDrawableRes))
    }

    // get country flag from drawable resource
    private fun getCountryFlagDrawableRes(countryFlag: String): Int {
        return when (countryFlag) {
            USA -> R.drawable.usa
            GERMANY -> R.drawable.germany
            RUSSIA -> R.drawable.russia
            EGYPT -> R.drawable.egypt
            else -> R.drawable.usa
        }
    }

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

    // handles the click on the Account
    fun onAccountClicked() = viewModelScope.launch {
        if (isCurrentUserNull) {
            emitAction(SettingsAction.Navigate(SettingsFragmentDirections.actionSettingsFragmentToAuthBottomSheetFragment()))
        } else {
            val showAccount = SettingsAction.ShowAccount(
                message = (R.string.want_sign_out),
                isError = true,
                action = { firebaseAuth.signOut() })
            emitAction(showAccount)
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

