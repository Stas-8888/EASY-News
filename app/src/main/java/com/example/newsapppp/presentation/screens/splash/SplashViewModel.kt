package com.example.newsapppp.presentation.screens.splash

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.interactors.sharedpreferences.GetSwitchPositionUseCase
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSwitchPosition: GetSwitchPositionUseCase,
) : BaseViewModel<SplashState, SplashAction>() {

    override val _state = MutableStateFlow<SplashState>(SplashState.Success)

    // Sets the day/night mode of the app.
    fun setupDayNightMode() {
        if (getSwitchPosition(Unit)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    // This function navigates to the login fragment after a delay of 3 seconds.
    fun navigateToLoginFragment() = viewModelScope.launch {
        delay(TimeUnit.SECONDS.toMillis(4))
        val direction = if (isCurrentUserNull) {
            SplashFragmentDirections.actionSplashFragmentToLoginFragment()
        } else {
            SplashFragmentDirections.actionSplashFragmentToMainFragment()
        }

        emitAction(SplashAction.Navigate(direction))
    }
}
