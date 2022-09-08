package com.example.newsapppp.presentation.fragments.main

import com.example.newsapppp.presentation.model.Article

sealed class MainState {

    object ShowLoading : MainState()

    object HideLoading : MainState()

    data class ShowArticles(val articles: List<Article>) : MainState()
}
