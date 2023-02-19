package com.example.newsapppp.presentation.ui.splash

import androidx.navigation.NavDirections

sealed class SplashAction {
    data class Navigate(val navigateTo: NavDirections) : SplashAction()
}
