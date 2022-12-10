package com.example.newsapppp.presentation.ui.search

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.interactors.retrofit.SearchNewsUseCase
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase,
    private val articleMapperToModel: ArticleMapperToModel
) : BaseViewModel<SearchState>() {

    override val _state = MutableStateFlow<SearchState>(SearchState.Error)
    override val state = _state.asStateFlow()

    fun getSearchRetrofit(searchQuery: String) = viewModelScope.launch {
        val data = searchNewsUseCase(searchQuery).articlesModel
        _state.emit(SearchState.ShowArticles(articleMapperToModel.articleToModelArticle(data)))
    }
}
