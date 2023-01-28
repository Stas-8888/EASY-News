package com.example.newsapppp.presentation.ui.search

import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.domain.interactors.articleRemote.SearchNewsUseCase
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase,
    private val articleMapper: ArticleMapper
) : BaseViewModel<SearchState>() {

    override val _state = MutableStateFlow<SearchState>(SearchState.Loading)
    override val state = _state.asStateFlow()

    fun searchTextListener(searchQuery: String) = launchCoroutine {
        if (searchQuery.isNotEmpty()) {
            val data = searchNewsUseCase(searchQuery).articlesModel
            emitState(SearchState.ShowArticles(articleMapper.mapToListArticle(data)))
        } else {
            emitState(SearchState.Error("Server error"))
        }
    }

    fun onItemClicked(article: Article){
        emitState(SearchState.NavigationArgs(SearchFragmentDirections.actionSearchFragmentToNewsFragment(article)))
    }
}
