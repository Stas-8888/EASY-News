package com.example.newsapppp.presentation.ui.main

import com.example.newsapppp.core.State
import com.example.newsapppp.presentation.model.Article

sealed class MainState : State {

    object ShowLoading : MainState()

    object HideLoading : MainState()

    data class ShowArticles(val articles: List<Article>) : MainState()
}
