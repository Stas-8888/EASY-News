package com.example.newsapppp.presentation.screens.splash

import androidx.navigation.NavDirections

sealed class SplashAction {
    data class Navigate(val navigateTo: NavDirections) : SplashAction()
}
