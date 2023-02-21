package com.example.newsapppp.presentation.ui.search

import com.example.newsapppp.R
import com.example.newsapppp.core.NetworkHandler
import com.example.newsapppp.domain.interactors.articleRemote.SearchNewsUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase,
    private val network: NetworkHandler,
    private val mapper: ArticleMapper,
) : BaseViewModel<SearchState, SearchAction>() {

    override val _state = MutableStateFlow<SearchState>(SearchState.Loading)
    override val state = _state.asStateFlow()

    override val _shared = MutableSharedFlow<SearchAction>()
    override val shared = _shared.asSharedFlow()

    fun searchTextListener(searchQuery: String) = launchCoroutine {
        if (network.isNetworkAvailable()) {
        if (searchQuery.isNotEmpty()) {
            val data = searchNewsUseCase(searchQuery).articlesModel
            emit(SearchState.ShowArticles(mapper.mapToListArticle(data)))
        } else {
            emitShared(SearchAction.ShowMessage(R.string.server_error))
        }
        } else {
            emitShared(SearchAction.ShowNetworkConnections(R.string.internet_disconnected))
        }
    }

    fun onItemClicked(article: Article) {
        emitShared(
            SearchAction.Navigate(
                SearchFragmentDirections.actionSearchFragmentToNewsFragment(
                    article
                )
            )
        )
    }
}
