package com.example.newsapppp.presentation.ui.search

import androidx.navigation.NavDirections
import com.example.newsapppp.core.mvvm.State
import com.example.newsapppp.presentation.model.Article

sealed class SearchState : State {
    object Loading : SearchState()
    class Error(val message: String) : SearchState()
    data class ShowArticles(val articles: List<Article>) : SearchState()

    data class NavigationArgs(val navDirections: NavDirections) : SearchState()
}
