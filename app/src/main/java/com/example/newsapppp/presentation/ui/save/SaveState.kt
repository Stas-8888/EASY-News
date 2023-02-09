package com.example.newsapppp.presentation.ui.save

import com.example.newsapppp.core.State
import com.example.newsapppp.presentation.model.Article

sealed class SaveState : State {

    object ShowLoading : SaveState()

    data class ShowArticles(
        val articles: List<Article>,
        val progressBar: Boolean,
        val state: Boolean,
        val exception: Int?) : SaveState()

    data class ShowDeleteDialog (val article: Article,val position : Int) : SaveState()

}
