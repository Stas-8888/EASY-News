package com.example.newsapppp.presentation.ui.main

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.preference.GetCountryFlagUseCase
import com.example.newsapppp.domain.interactors.retrofit.GetNewsUseCase
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import com.example.newsapppp.presentation.extensions.launchCoroutine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val articleMapperToModel: ArticleMapperToModel,
    private val getCountryFlagUseCase: GetCountryFlagUseCase
) : BaseViewModel<MainState>() {

    override val _state = MutableStateFlow<MainState>(MainState.ShowLoading)
    override val state = _state.asStateFlow()

    fun getNews(category: String) = launchCoroutine {
        val data = getNewsUseCase(category).articlesModel
            .filter { it.urlToImage != null }
        if (data.isNotEmpty()) {
            emitState(MainState.ShowArticles(articleMapperToModel.articleToModelArticle(data)))
        } else {
            emitState(MainState.Error(R.string.error))
        }
    }

    //Fix
    fun getCountryFlag(): String {
        return getCountryFlagUseCase(Unit)
    }

    fun showOrHideFloatButton(getFirstNewsPosition: Int) = launchCoroutine {
        if (getFirstNewsPosition < 1)
            emitState(MainState.ShowBottom) else emitState(MainState.HideBottom)
    }
}
