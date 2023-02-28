package com.example.newsapppp.presentation.screens.search

import com.example.newsapppp.R
import com.example.newsapppp.core.NetworkHandlerContract
import com.example.newsapppp.domain.interactors.articleremote.SearchNewsUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase,
    private val network: NetworkHandlerContract,
    private val mapper: ArticleMapper,
) : BaseViewModel<SearchState, SearchAction>() {

    override val _state = MutableStateFlow<SearchState>(SearchState.Loading)
    override val _shared = MutableSharedFlow<SearchAction>()

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
