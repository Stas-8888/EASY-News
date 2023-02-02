package com.example.newsapppp.presentation.ui.news

import com.example.newsapppp.core.State
sealed class NewsState : State {
    data class ShowFavoriteIcon(val favoriteIcon: Int) : NewsState()
    data class HideFavoriteIcon(val favoriteIcon: Int) : NewsState()

    data class SaveFavoriteArticle(
        val status: Int,
        val favoriteIcon: Int
    ) : NewsState()

    data class DeleteFavoriteArticle(
        val status: Int,
        val favoriteIcon: Int
    ) : NewsState()

    data class Error(val message: Int) : NewsState()

}
