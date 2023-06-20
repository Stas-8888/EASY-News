package com.example.newsapppp.presentation.screens.search

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.example.newsapppp.domain.use_case.articles.remote.SearchArticlesUseCase
import com.example.newsapppp.presentation.extensions.isOffline
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchArticles: SearchArticlesUseCase,
    private val mapper: ArticleMapper
) : BaseViewModel<SearchState, SearchAction>() {

    override val _state = MutableStateFlow<SearchState>(SearchState.Loading)

    /**
     * Handles search query text input and displays articles matching the query.
     */
    fun searchQueryListener(searchQuery: String) = when {
        searchQuery.trim().isEmpty() -> emitAction(SearchAction.ShowMessage(R.string.empty_field))
        isOffline() -> emitAction(SearchAction.ShowNetworkDialog(R.string.internet_disconnected))
        else -> performSearch(searchQuery)
    }

    /**
     * Fetches and emits the search results, mapped to a list of Article models.
     */
    private fun performSearch(searchQuery: String) = viewModelScope.launch {
        try {
            val data = searchArticles(searchQuery).articlesModel
            emit(SearchState.ShowArticles(mapper.mapToListArticle(data)))
        } catch (e: Exception) {
            emitAction(SearchAction.ShowMessage(R.string.server_error))
        }
    }

    /**
     * Handles click on article item in recycler view and navigates to news fragment.
     */
    fun onItemAdapterClicked(article: Article) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(article)
        emitAction(SearchAction.Navigate(action))
    }
}
