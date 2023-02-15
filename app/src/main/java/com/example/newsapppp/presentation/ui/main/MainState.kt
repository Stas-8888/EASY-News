package com.example.newsapppp.presentation.ui.main

import androidx.navigation.NavDirections
import androidx.paging.PagingData
import com.example.newsapppp.core.State
import com.example.newsapppp.presentation.model.Article

sealed class MainState : State {

    object ShowLoading : MainState()

    data class Error(val exception: Int) : MainState()

    data class SetupUi(val setupArticleNews: PagingData<Article>, val countryFlag: String) :
        MainState()

    data class BottomVisibility(val state: Boolean) : MainState()

    data class AdapterClicked(val navigate: NavDirections) : MainState()
    data class SettingsClicked(val navigate: Int) : MainState()
}
