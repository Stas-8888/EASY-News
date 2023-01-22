package com.example.newsapppp.presentation.ui.save

import com.example.newsapppp.core.State
import com.example.newsapppp.presentation.model.Article

sealed class SaveState : State {

    object ShowLoading : SaveState()

    data class ShowArticles(val articles: List<Article>) : SaveState()

    data class ShowDeleteDialog (val article: Article,val position : Int) : SaveState()

    data class Error(val exception: Int) : SaveState()
}
