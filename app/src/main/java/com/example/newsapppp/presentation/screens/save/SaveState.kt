package com.example.newsapppp.presentation.screens.save

import com.example.newsapppp.presentation.model.Article

sealed class SaveState {

    object ShowLoading : SaveState()

    data class ShowArticles(
        val articles: List<Article>,
        val progressBar: Boolean,
        val state: Boolean,
        val exception: Int?
    ) : SaveState()
}
