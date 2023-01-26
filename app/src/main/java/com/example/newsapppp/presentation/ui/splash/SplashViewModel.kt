package com.example.newsapppp.presentation.ui.splash

import androidx.appcompat.app.AppCompatDelegate
import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.preference.GetSwitchPositionUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getSwitchPositionUseCase: GetSwitchPositionUseCase
) : BaseViewModel<SplashState>() {

    override val _state = MutableStateFlow<SplashState>(SplashState.Success)
    override val state = _state.asStateFlow()

    private fun getSwitchPosition(): Boolean {
        return getSwitchPositionUseCase(Unit)
    }

    fun setupDayNightMode() {
        if (getSwitchPosition()) {
            AppCompatDelegate
                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        } else {
            AppCompatDelegate
                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    fun delayScreen() = launchCoroutine {
        delay(TimeUnit.SECONDS.toMillis(3))
        emitState(SplashState.Navigate(R.id.loginFragment))
    }
}
