package com.example.newsapppp.presentation.ui.news

sealed class NewsAction {

    data class Navigate(val navigateTo: Int) : NewsAction()
}
