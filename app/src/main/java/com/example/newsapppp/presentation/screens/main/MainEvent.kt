package com.example.newsapppp.presentation.screens.main

import androidx.navigation.NavDirections

sealed class MainEvent {
    data class OnProfileClicked(val navigateTo11: NavDirections) : MainEvent()
    data class OnAdapterItemClicked(val navigateTo: NavDirections) : MainEvent()
}
