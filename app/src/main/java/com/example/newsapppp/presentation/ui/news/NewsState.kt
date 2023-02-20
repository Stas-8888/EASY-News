package com.example.newsapppp.presentation.ui.news

sealed class NewsState {
    data class SetupFavoriteIcon(val favoriteIcon: Int) : NewsState()
}
