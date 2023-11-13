package com.example.newsapppp.presentation.screens.activity

sealed class MainActivityState {
    data class BottomNavigationState (val state: Boolean): MainActivityState()
}
