package com.example.newsapppp.presentation.ui.main

import com.example.newsapppp.R
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
    private val articleMapper: ArticleMapper,
    private val getCountryFlag: GetCountryFlagUseCase,
) : BaseViewModel<MainState>() {

    override val _state = MutableStateFlow<MainState>(MainState.ShowLoading)
    override val state = _state.asStateFlow()

    fun setupArticleNews(category: String) = launchCoroutine {
        val data = getNews(category).articlesModel.filter { it.urlToImage != null }
        if (data.isNotEmpty()) {
            emit(MainState.ShowArticles(articleMapper.mapToListArticle(data)))
        } else {
            emit(MainState.Error(R.string.error))
        }
    }

    fun setupCountry() {
        emit(MainState.GetCountryFlag(getCountryFlag(Unit)))
    }

    fun showOrHideFloatButton(getFirstNewsPosition: Int) {
        if (getFirstNewsPosition < 1)
            emit(MainState.ShowBottom) else emit(MainState.HideBottom)
    }
}
