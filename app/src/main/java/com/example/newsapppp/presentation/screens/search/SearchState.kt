package com.example.newsapppp.presentation.screens.search

import com.example.newsapppp.presentation.model.Article

sealed class SearchState {
    object Loading : SearchState()
    data class ShowArticles(val articles: List<Article>) : SearchState()
}
