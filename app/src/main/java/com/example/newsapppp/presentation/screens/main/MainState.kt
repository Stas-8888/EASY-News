package com.example.newsapppp.presentation.screens.main

import androidx.paging.PagingData
import com.example.newsapppp.presentation.model.Article

sealed class MainState {
    data class ShowUI(val article: PagingData<Article>, val countryFlag: String) : MainState()
    data class BottomVisibility(val isVisible: Boolean) : MainState()
}
