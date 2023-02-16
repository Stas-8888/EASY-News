package com.example.newsapppp.presentation.ui.search

sealed class SearchAction {

    data class Navigate(val navigateTo: Int) : SearchAction()
}
