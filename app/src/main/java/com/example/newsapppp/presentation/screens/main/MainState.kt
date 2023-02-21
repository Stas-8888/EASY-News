package com.example.newsapppp.presentation.screens.main

import androidx.paging.PagingData
import com.example.newsapppp.presentation.model.Article

sealed class MainState {
    object ShowLoading : MainState()
    data class SetupUI(val article: PagingData<Article>, val countryFlag: String) : MainState()
    data class BottomVisibility(val state: Boolean) : MainState()
}
