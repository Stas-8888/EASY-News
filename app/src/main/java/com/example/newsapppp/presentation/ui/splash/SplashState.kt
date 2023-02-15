package com.example.newsapppp.presentation.ui.splash

import com.example.newsapppp.core.mvvm.State

sealed class SplashState : State {

    object Success : SplashState()

    data class Navigate(val navigateTo: Int) : SplashState()
}
