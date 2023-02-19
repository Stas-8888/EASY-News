package com.example.newsapppp.presentation.ui.splash

import androidx.appcompat.app.AppCompatDelegate
import com.example.newsapppp.domain.interactors.preference.GetSwitchPositionUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSwitchPosition: GetSwitchPositionUseCase,
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel<SplashState, SplashAction>() {

    override val _state = MutableStateFlow<SplashState>(SplashState.Success)
    override val state = _state.asStateFlow()

    override val _shared = MutableSharedFlow<SplashAction>()
    override val shared = _shared.asSharedFlow()

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
