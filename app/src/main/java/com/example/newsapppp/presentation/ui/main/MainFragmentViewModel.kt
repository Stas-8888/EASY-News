package com.example.newsapppp.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.interactors.preference.GetCountryFlagUseCase
import com.example.newsapppp.domain.interactors.retrofit.GetNewsUseCase
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val articleMapperToModel: ArticleMapperToModel,
    private val getCountryFlagUseCase: GetCountryFlagUseCase,
) : BaseViewModel<MainState>() {

    override val _state = MutableStateFlow<MainState>(MainState.ShowLoading)
    override val state = _state.asStateFlow()

    fun getNews(countryCode: String, category: String) = viewModelScope.launch {
        _state.emit(MainState.ShowLoading)
        val data = getNewsUseCase.getNews(
            countryCode = countryCode,
            category = category
        ).articlesModel.filter { it.urlToImage != null }

        _state.emit(MainState.ShowArticles(articleMapperToModel.articleToModelArticle(data)))
    }

    fun getCountryFlag(): String {
        return getCountryFlagUseCase(Unit)
    }
}
