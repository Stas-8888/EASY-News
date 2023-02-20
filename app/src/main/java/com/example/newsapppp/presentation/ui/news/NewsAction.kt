package com.example.newsapppp.presentation.ui.news

sealed class NewsAction {
    data class Message(val message: Int) : NewsAction()
    data class FavoriteIcon(val status: Int, val favoriteIcon: Int) : NewsAction()
}
