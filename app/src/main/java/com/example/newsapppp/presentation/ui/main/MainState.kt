package com.example.newsapppp.presentation.ui.main

import androidx.paging.PagingData
import com.example.newsapppp.presentation.model.Article

sealed class MainState {
    object ShowLoading : MainState()
    data class GetPagingAllArticle(val article: PagingData<Article>) : MainState()
    data class CountryFlag(val countryFlag: String) : MainState()
    data class BottomVisibility(val state: Boolean) : MainState()
}
