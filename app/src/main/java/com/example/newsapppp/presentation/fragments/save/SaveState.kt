package com.example.newsapppp.presentation.fragments.save

import com.example.newsapppp.presentation.fragments.base.State
import com.example.newsapppp.presentation.model.Article

sealed class SaveState : State {

    object ShowLoading : SaveState()

    data class ShowArticles(val articles: List<Article>) : SaveState()
}
