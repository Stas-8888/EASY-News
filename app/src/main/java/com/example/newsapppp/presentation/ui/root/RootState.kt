package com.example.newsapppp.presentation.ui.root

import com.example.newsapppp.core.State

sealed class RootState : State {

    data class NavigationToMain(val mainFragment: Int) : RootState()

    data class Navigation(val navigateFragment: Int) : RootState()

    data class InterceptorErrors(val error: String) : RootState()
}
