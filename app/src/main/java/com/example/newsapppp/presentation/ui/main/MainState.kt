package com.example.newsapppp.presentation.ui.main

import androidx.paging.PagingData
import com.example.newsapppp.core.State
import com.example.newsapppp.presentation.model.Article

sealed class MainState : State {

    object ShowLoading : MainState()

    data class ShowArticles(val articles: PagingData<Article>) : MainState()

    data class Error(val exception: Int) : MainState()

    data class Visibility(val state: Boolean) : MainState()

    data class CountryFlag(val countryFlag: String) : MainState()
}
