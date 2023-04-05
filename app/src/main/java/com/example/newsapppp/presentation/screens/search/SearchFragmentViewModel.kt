package com.example.newsapppp.presentation.screens.search

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.articles.remote.SearchArticlesUseCase
import com.example.newsapppp.presentation.extensions.isOffline
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchArticles: SearchArticlesUseCase,
    private val mapper: ArticleMapper
) : BaseViewModel<SearchState, SearchAction>() {

    override val _state = MutableStateFlow<SearchState>(SearchState.Loading)

    /**
     * Handles search query text input and displays articles matching the query.
     */
    fun searchQueryListener(searchQuery: String) = viewModelScope.launch {
        when {
            isOffline() -> emitAction(SearchAction.ShowNetworkDialog(R.string.internet_disconnected))
            searchQuery.isNotEmpty() -> try {
                val data = searchArticles(searchQuery).articlesModel
                emit(SearchState.ShowArticles(mapper.mapToListArticle(data)))
            } catch (e: Exception) {
                emitAction(SearchAction.ShowMessage(R.string.server_error))
            }
        }
    }

    /**
     * Handles click on article item in recycler view and navigates to news fragment.
     */
    fun onItemAdapterClicked(article: Article) {
        val action = SearchFragmentDirections.actionSearchFragmentToNewsFragment(article)
        emitAction(SearchAction.Navigate(action))
    }
}
