package com.example.newsapppp.presentation.screens.activity

sealed class MainActivityState {
    object Success : MainActivityState()
    object Failure : MainActivityState()
}
