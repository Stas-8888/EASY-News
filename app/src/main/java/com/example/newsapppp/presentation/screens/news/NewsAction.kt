package com.example.newsapppp.presentation.screens.news

sealed class NewsAction {
    data class ShowMessage(val message: Int) : NewsAction()
    data class ShowFavoriteIcon(val status: Int, val favoriteIcon: Int) : NewsAction()
}
