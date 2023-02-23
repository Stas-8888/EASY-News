package com.example.newsapppp.presentation.screens.news

sealed class NewsState {
    data class ShowFavoriteIcon(val favoriteIcon: Int) : NewsState()
}
