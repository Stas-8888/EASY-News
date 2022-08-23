package com.example.newsapppp.presentation.fragments

import com.example.newsapppp.presentation.model.Article

sealed class SaveState {

    object ShowLoading : SaveState()

    object HideLoading : SaveState()

    object ShowErrorScreen : SaveState()

    data class ShowArticles(val articles: List<Article>) : SaveState()
}
