package com.example.newsapppp.presentation.ui.main

import com.example.newsapppp.core.extensions.launchCoroutine
import com.example.newsapppp.domain.interactors.preference.GetCountryFlagUseCase
import com.example.newsapppp.domain.interactors.retrofit.GetNewsUseCase
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
    private val getCountryFlagUseCase: GetCountryFlagUseCase
) : BaseViewModel<MainState>() {

    override val _state = MutableStateFlow<MainState>(MainState.ShowLoading)
    override val state = _state.asStateFlow()

    fun getNews(category: String) = launchCoroutine {
        _state.emit(MainState.ShowLoading)
        val country = getCountryFlag()
        val data = getNewsUseCase.getNews(
            countryCode = country,
            category = category
        ).articlesModel.filter { it.urlToImage != null }

        _state.emit(MainState.ShowArticles(articleMapperToModel.articleToModelArticle(data)))
    }

    fun getCountryFlag(): String {
        return getCountryFlagUseCase(Unit)
    }

    fun showOrHideFloatButton(getFirstNewsPosition: Int) = launchCoroutine {
        if (getFirstNewsPosition < 1)
            _state.emit(MainState.ShowBottom) else _state.emit(MainState.HideBottom)
    }
}
