package com.example.newsapppp.presentation.screens.splash

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.interactors.sharedpreferences.GetSwitchPositionUseCase
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSwitchPosition: GetSwitchPositionUseCase,
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel<SplashState, SplashAction>() {

    override val _state = MutableStateFlow<SplashState>(SplashState.Success)
    override val _action = MutableSharedFlow<SplashAction>()

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
        delay(TimeUnit.SECONDS.toMillis(3))
        val direction = if (firebaseAuth.currentUser != null) {
            SplashFragmentDirections.actionSplashFragmentToMainFragment()
        } else {
            SplashFragmentDirections.actionSplashFragmentToLoginFragment()
        }
        emitAction(SplashAction.Navigate(direction))
    }
}
