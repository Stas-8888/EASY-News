package com.example.newsapppp.presentation.ui.splash

import com.example.newsapppp.core.State

sealed class SplashState: State {

    object Success : SplashState()

    object Error : SplashState()

}
