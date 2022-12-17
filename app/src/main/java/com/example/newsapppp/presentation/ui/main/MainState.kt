package com.example.newsapppp.presentation.ui.main

import com.example.newsapppp.core.State
import com.example.newsapppp.presentation.model.Article

sealed class MainState : State {

    object ShowLoading : MainState()

    data class ShowArticles(val articles: List<Article>) : MainState()

    object ShowBottom : MainState()

    object HideBottom : MainState()

    data class GetCountryFlag(val getCountryFlag: String) : MainState()

}
