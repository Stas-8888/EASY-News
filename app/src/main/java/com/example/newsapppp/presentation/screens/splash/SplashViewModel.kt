package com.example.newsapppp.presentation.screens.splash

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.common.Constants.DURATION_SPLASH
import com.example.newsapppp.domain.use_case.shared_preferences.GetSwitchPositionUseCase
import com.example.newsapppp.presentation.extensions.isCurrentUserNull
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

    /**
     * Sets the day/night mode of the app.
     */
    fun setupDayNightMode() = when (getSwitchPosition(Unit)) {
        true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    /**
     * This function navigates to the login fragment after a delay of 5 seconds.
     */
    fun navigateToLoginFragment() = viewModelScope.launch {
        delay(TimeUnit.SECONDS.toMillis(DURATION_SPLASH))
        val direction = when {
            isCurrentUserNull() -> SplashFragmentDirections.actionSplashFragmentToSignInFragment()
            else -> SplashFragmentDirections.actionSplashFragmentToMainFragment()
        }
        emitAction(SplashAction.Navigate(direction))
    }
}
