package com.example.newsapppp.presentation.screens.activity

sealed class MainActivityState {
    object Success : MainActivityState()
    data class Ui(val destination: Unit) : MainActivityState()
}
