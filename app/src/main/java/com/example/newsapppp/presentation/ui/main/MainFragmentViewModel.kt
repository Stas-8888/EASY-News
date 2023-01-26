package com.example.newsapppp.presentation.ui.main

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.articleRemote.GetNewsUseCase
import com.example.newsapppp.domain.interactors.preference.GetCountryFlagUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val articleMapperToModel: ArticleMapperToModel,
    private val getCountryFlagUseCase: GetCountryFlagUseCase,
) : BaseViewModel<MainState>() {

    override val _state = MutableStateFlow<MainState>(MainState.ShowLoading)
    override val state = _state.asStateFlow()

    fun getNews(category: String) = launchCoroutine {
        val data = getNewsUseCase(category).articlesModel.filter { it.urlToImage != null }
        if (data.isNotEmpty()) {
            emitState(MainState.ShowArticles(articleMapperToModel.articleToModelArticle(data)))
        } else {
            emitState(MainState.Error(R.string.error))
        }
    }

    fun getCountryFlag() {
        emitState(MainState.GetCountryFlag(getCountryFlagUseCase(Unit)))
    }

    fun showOrHideFloatButton(getFirstNewsPosition: Int) {
        if (getFirstNewsPosition < 1)
            emitState(MainState.ShowBottom) else emitState(MainState.HideBottom)
    }
}
