package com.example.newsapppp.presentation.screens.news

sealed class NewsState {
    data class SetupFavoriteIcon(val favoriteIcon: Int) : NewsState()
}
