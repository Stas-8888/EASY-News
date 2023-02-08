package com.example.newsapppp.presentation.ui.main

import androidx.paging.filter
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
    private val mapper: ArticleMapper,
    getCountryFlag: GetCountryFlagUseCase,
) : BaseViewModel<MainState>() {

    override val _state = MutableStateFlow<MainState>(MainState.CountryFlag(getCountryFlag(Unit)))
    override val state = _state.asStateFlow()

    fun setupArticleNews(category: String) = launchCoroutine {
        try {
            getNews(category).collect() {
                val data = it.filter { article ->
                    article.urlToImage != null && article.title != null
                }
                emit(MainState.ShowArticles(mapper.mapToPagingArticle(data)))
            }
        } catch (e: Exception) {
            emit(MainState.Error(R.string.server_error))
        }
    }

    fun showOrHideFloatButton(getFirstNewsPosition: Int) {
        if (getFirstNewsPosition < 1)
            emit(MainState.ShowBottom) else emit(MainState.HideBottom)
    }
}
