package com.example.newsapppp.presentation.ui.splash

sealed class SplashAction {

    data class Navigate(val navigateTo: Int) : SplashAction()
}
