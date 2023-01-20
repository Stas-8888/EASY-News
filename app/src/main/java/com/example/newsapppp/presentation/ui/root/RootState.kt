package com.example.newsapppp.presentation.ui.root

import com.example.newsapppp.core.State

sealed class RootState : State {
    object Loading : RootState()
    class Error(val message: String) : RootState()
    data class Navigation(
        val mainFragment: Int,
        val saveFragment: Int,
        val searchFragment: Int
    ) : RootState()
}
