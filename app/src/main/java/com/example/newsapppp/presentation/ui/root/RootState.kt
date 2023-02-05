package com.example.newsapppp.presentation.ui.root

import com.example.newsapppp.core.State

sealed class RootState : State {
    data class Navigation(
        val mainFragment: Int,
        val saveFragment: Int,
        val searchFragment: Int
    ) : RootState()

    data class InterceptorErrors(val error: String) : RootState()

}
