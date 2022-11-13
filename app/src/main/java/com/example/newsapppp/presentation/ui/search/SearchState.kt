package com.example.newsapppp.presentation.ui.search

import com.example.newsapppp.core.State
import com.example.newsapppp.presentation.model.Article

sealed class SearchState : State {

    object ShowLoading : SearchState()

    object HideLoading : SearchState()

    data class ShowArticles(val articles: List<Article>) : SearchState()

}
