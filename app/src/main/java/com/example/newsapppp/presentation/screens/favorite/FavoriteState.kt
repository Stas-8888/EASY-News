package com.example.newsapppp.presentation.screens.favorite

import com.example.newsapppp.presentation.model.Article

sealed class FavoriteState {

    object Loading : FavoriteState()

    data class ShowArticles(
        val articles: List<Article>,
        val progressBar: Boolean,
        val state: Boolean,
        val exception: Int?
    ) : FavoriteState()
}
