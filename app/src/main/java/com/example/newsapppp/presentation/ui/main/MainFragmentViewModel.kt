package com.example.newsapppp.presentation.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.newsapppp.domain.interactors.articleRemote.GetNewsUseCase
import com.example.newsapppp.domain.interactors.preference.GetCountryFlagUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getNews: GetNewsUseCase,
    private val mapper: ArticleMapper,
    private val getCountryFlag: GetCountryFlagUseCase,
) : BaseViewModel<MainState>() {

    override val _state = MutableStateFlow<MainState>(MainState.ShowLoading)
    override val state = _state.asStateFlow()

    fun setupCountryFlag() {
        emit(MainState.CountryFlag(getCountryFlag(Unit)))
    }

    fun setupArticleNews(category: String) = launchCoroutine {
        getNews(category).cachedIn(viewModelScope).collect() {
            val data = it.filter { article ->
                article.urlToImage != null && article.title != null
            }
            emit(MainState.ShowArticles(mapper.mapToPagingArticle(data)))
        }
    }

    fun showOrHideFloatButton(getFirstNewsPosition: Int) {
        if (getFirstNewsPosition < 1)
            emit(MainState.Visibility(false)) else emit(MainState.Visibility(true))
    }
}
