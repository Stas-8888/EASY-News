package com.example.newsapppp.presentation.ui.news

import com.example.newsapppp.core.mvvm.State
sealed class NewsState : State {
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
