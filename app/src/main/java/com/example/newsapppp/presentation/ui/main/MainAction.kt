package com.example.newsapppp.presentation.ui.main

sealed class MainAction {

    data class Navigate(val navigateTo: Int) : MainAction()
}
