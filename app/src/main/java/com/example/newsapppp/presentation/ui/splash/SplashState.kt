package com.example.newsapppp.presentation.ui.splash

sealed class SplashState {

    object Success : SplashState()

    data class Navigate(val navigateTo: Int) : SplashState()
}
