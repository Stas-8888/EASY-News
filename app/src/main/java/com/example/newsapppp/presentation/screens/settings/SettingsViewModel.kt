package com.example.newsapppp.presentation.screens.settings

import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.example.newsapppp.domain.use_case.shared_preferences.GetCountryFlagUseCase
import com.example.newsapppp.domain.use_case.shared_preferences.GetSwitchPositionUseCase
import com.example.newsapppp.domain.use_case.shared_preferences.SaveCountryFlagUseCase
import com.example.newsapppp.domain.use_case.shared_preferences.SaveSwitchPositionUseCase
import com.example.newsapppp.presentation.extensions.isCurrentUserNull
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val USA = "us"
private const val GERMANY = "de"
private const val RUSSIA = "ru"
private const val EGYPT = "eg"

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val saveCountryFlag: SaveCountryFlagUseCase,
    private val saveSwitchPosition: SaveSwitchPositionUseCase,
    private var getCountryFlag: GetCountryFlagUseCase,
    private var getThemes: GetSwitchPositionUseCase
) : BaseViewModel<SettingsState, SettingsAction>() {

    override val _state =
        MutableStateFlow<SettingsState>(SettingsState.SetCurrentCountry(R.drawable.usa))

    /**
     * Sets up the UI of the screen.
     */
    fun updateUi() {
        val theme = getThemes(Unit).not()
        val email = firebaseAuth.currentUser?.email
        val flagDrawableRes = getCountryFlagDrawableRes(getCountryFlag(Unit))
        emit(SettingsState.ShowUi(theme = theme, email = email, flag = flagDrawableRes))
    }

    /**
     * get country flag from drawable resource.
     */
    private fun getCountryFlagDrawableRes(countryFlag: String): Int {
        return when (countryFlag) {
            USA -> R.drawable.usa
            GERMANY -> R.drawable.germany
            RUSSIA -> R.drawable.russia
            EGYPT -> R.drawable.egypt
            else -> R.drawable.usa
        }
    }

    /**
     * Update the day/night mode of the app and save the switch position.
     */
    fun onSwitchDayNightClicked(enabled: Boolean) = viewModelScope.launch {
        if (enabled) {
            saveSwitchPosition(false)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            saveSwitchPosition(true)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    /**
     * Handles the click on the Account button.
     * If the current user is null, navigates to the authentication screen.
     * Otherwise, shows a confirmation dialog to sign out.
     */
    fun onAccountClicked() = when {
        isCurrentUserNull() -> navigateToAuthentication()
        else -> showSignOutConfirmation()
    }

    /**
     * Navigates to the authentication screen using a navigation action and emits it.
     */
    private fun navigateToAuthentication() {
        val action = SettingsFragmentDirections.actionSettingsFragmentToAuthBottomSheetFragment()
        emitAction(SettingsAction.Navigate(action))
    }

    /**
     * Shows a confirmation dialog to sign out using an action with a message and a callback to sign out.
     * Emits the action to be handled by the view.
     */
    private fun showSignOutConfirmation() {
        val signOutConfirmation = SettingsAction.ShowConfirmation(
            message = (R.string.want_sign_out),
            isError = true,
            isPositiveAction = { firebaseAuth.signOut() })

        emitAction(signOutConfirmation)
    }

    /**
     * Sets up the popup menu of the settings screen.
     */
    fun setupPopupMenu(item: MenuItem) {
        when (item.itemId) {
            R.id.us -> popupData(USA, R.drawable.usa, R.string.american_news)
            R.id.ru -> popupData(RUSSIA, R.drawable.russia, R.string.russia_news)
            R.id.egipt -> popupData(EGYPT, R.drawable.egypt, R.string.egypt_news)
            R.id.germany -> popupData(GERMANY, R.drawable.germany, R.string.germany_news)
        }
    }

    /**
     * Saves the selected country flag and emits a SettingsState object to update the UI.
     * Displays a message to the user with the selected country's news.
     */
    private fun popupData(flag: String, currentCountry: Int, message: Int) = viewModelScope.launch {
        saveCountryFlag(flag)
        emit(SettingsState.SetCurrentCountry(currentCountry))
        emitAction((SettingsAction.ShowMessage(message)))
    }

    /**
     * Handles the click on the profile image options menu.
     */
    fun onProfileImageClicked(item: MenuItem, launchGallery: () -> Unit) {
        when (item.itemId) {
            R.id.galleryMenu -> launchGallery.invoke()
            R.id.cameraMenu -> {}
        }
    }
}
