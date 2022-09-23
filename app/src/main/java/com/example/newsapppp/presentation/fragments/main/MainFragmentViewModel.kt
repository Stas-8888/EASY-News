package com.example.newsapppp.presentation.fragments.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.usecase.GetCountryFlagUseCase
import com.example.newsapppp.domain.usecase.GetNewsUseCase
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    app: Application,
    private val getNewsUseCase: GetNewsUseCase,
    private val articleMapperToModel: ArticleMapperToModel,
    private val getCountryFlagUseCase: GetCountryFlagUseCase,
) : AndroidViewModel(app) {

    private val _state = MutableStateFlow<MainState>(MainState.ShowLoading)
    val state = _state.asStateFlow()

    fun getNewsRetrofit(countryCode: String, category: String) = viewModelScope.launch {
        getNews(countryCode, category)
    }

    private suspend fun getNews(countryCode: String, category: String) {
        _state.emit(MainState.ShowLoading)
        val data = getNewsUseCase.getNews(
            countryCode = countryCode,
            category = category
        ).articlesModel.filter { it.urlToImage != null }
        _state.emit(MainState.ShowArticles(articleMapperToModel.articleToModelArticle(data)))
        _state.emit(MainState.HideLoading)
    }

    fun getCountryFlag(): String {
        return getCountryFlagUseCase.getCountryFlag()
    }
}
