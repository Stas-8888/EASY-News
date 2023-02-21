package com.example.newsapppp.presentation.screens.splash

import androidx.appcompat.app.AppCompatDelegate
import com.example.newsapppp.domain.interactors.preference.GetSwitchPositionUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSwitchPosition: GetSwitchPositionUseCase,
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel<SplashState, SplashAction>() {

    override val _state = MutableStateFlow<SplashState>(SplashState.Success)
    override val _shared = MutableSharedFlow<SplashAction>()

    fun setupDayNightMode() {
        if (getSwitchPosition(Unit)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    fun navigateToLoginFragment() = launchCoroutine {
        delay(TimeUnit.SECONDS.toMillis(3))
        if (firebaseAuth.currentUser != null) {
            emitShared(SplashAction.Navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment()))
        } else {
            emitShared(SplashAction.Navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment()))
        }
    }
}
