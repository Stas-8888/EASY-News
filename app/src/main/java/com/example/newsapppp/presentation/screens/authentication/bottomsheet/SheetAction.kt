package com.example.newsapppp.presentation.screens.authentication.bottomsheet

import androidx.navigation.NavDirections

sealed class SheetAction {
    data class Navigate(val navigateTo: NavDirections) : SheetAction()
}
