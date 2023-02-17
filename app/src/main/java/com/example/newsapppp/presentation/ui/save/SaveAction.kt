package com.example.newsapppp.presentation.ui.save

import androidx.navigation.NavDirections

sealed class SaveAction {

    data class Navigate(val navigateTo: NavDirections) : SaveAction()
}
