package com.example.newsapppp.presentation.fragments.save

import com.example.newsapppp.presentation.model.Article

sealed class SaveState {

    object ShowLoading : SaveState()

    data class ShowArticles(val articles: List<Article>) : SaveState()
}
