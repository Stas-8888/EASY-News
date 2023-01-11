package com.example.newsapppp.presentation.ui.search

import com.example.newsapppp.presentation.utils.extensions.launchCoroutine
import com.example.newsapppp.domain.interactors.retrofit.SearchNewsUseCase
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase,
    private val articleMapperToModel: ArticleMapperToModel
) : BaseViewModel<SearchState>() {

    override val _state = MutableStateFlow<SearchState>(SearchState.Loading)
    override val state = _state.asStateFlow()

    fun searchTextListener(searchQuery: String) = launchCoroutine {
        if (searchQuery.isNotEmpty()) {
            val data = searchNewsUseCase(searchQuery).articlesModel
            emit(SearchState.ShowArticles(articleMapperToModel.articleToModelArticle(data)))
        } else {
            emit(SearchState.Error("Server error"))
        }
    }
}
