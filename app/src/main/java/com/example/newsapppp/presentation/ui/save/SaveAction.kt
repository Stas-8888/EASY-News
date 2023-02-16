package com.example.newsapppp.presentation.ui.save

sealed class SaveAction {

    data class Navigate(val navigateTo: Int) : SaveAction()
}
