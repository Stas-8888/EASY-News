package com.example.newsapppp.presentation.ui.news

sealed class NewsState {
    data class ShowFavoriteIcon(val favoriteIcon: Int) : NewsState()

    data class SaveFavorite(
        val status: Int,
        val favoriteIcon: Int
    ) : NewsState()

    data class DeleteFavorite(
        val status: Int,
        val favoriteIcon: Int
    ) : NewsState()

    data class Error(val message: Int) : NewsState()

}
