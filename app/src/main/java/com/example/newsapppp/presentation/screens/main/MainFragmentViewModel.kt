package com.example.newsapppp.presentation.screens.main

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapppp.R
import com.example.newsapppp.data.articles.remote.interceptor.ErrorsInterceptorContract
import com.example.newsapppp.domain.interactors.articles.remote.FetchedArticlesUseCase
import com.example.newsapppp.domain.interactors.sharedpreferences.GetCountryFlagUseCase
import com.example.newsapppp.presentation.extensions.isOffline
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private val categories = listOf(
    "Technology", "Sports", "Science", "Entertainment", "Business", "Health"
)

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val mapper: ArticleMapper,
    private val getCountryFlag: GetCountryFlagUseCase,
    private val fetchedArticles: FetchedArticlesUseCase,
    private val interceptorErrors: ErrorsInterceptorContract
) : BaseViewModel<MainState, MainAction>() {

    override val _state = MutableStateFlow<MainState>(MainState.ShowLoading)

    /**
     * Sets up UI for main fragment with news for first category.
     * Sets up news for a specific tab/category.
     */
    fun fetchAndShowArticles(tab: TabLayout.Tab? = null) = when {
        isOffline() -> emitAction(MainAction.ShowNetworkDialog(R.string.internet_disconnected))
        else -> fetchAndEmitArticles(tab)
    }

    /**
     * Fetches articles for the specified category and emits the UI state to display them.
     * If no tab is provided, fetches articles for the first category.
     * @param tab the tab representing the category of news to fetch and display
     */
    private fun fetchAndEmitArticles(tab: TabLayout.Tab?) = viewModelScope.launch {
        try {
            val category = tab?.let { categories.getOrNull(it.position) } ?: categories.first()
            val getArticles = fetchedArticles(category).cachedIn(viewModelScope)
            getArticles.collect { articles ->
                val mappedArticles = mapper.mapToPagingArticle(articles)
                emit(MainState.ShowUI(mappedArticles, getCountryFlag(Unit)))
            }
        } catch (e: Exception) {
            emitAction(MainAction.ShowMessage(R.string.error))
        }
    }

    /**‹
     * Shows/hides float button based on position of first news article.
     */
    fun showOrHideFloatButton(getFirstNewsPosition: Int) {
        val isVisible = getFirstNewsPosition >= 1
        emit(MainState.BottomVisibility(isVisible))
    }

    /**
     * Handles click on settings button.
     */
    fun onBtSettingsClicked() {
        val action = MainFragmentDirections.actionMainFragmentToSettingsFragment()
        emitAction(MainAction.Navigate(action))
    }

    /**
     * Handles click on news article in recycler view.
     */
    fun onNewsAdapterClicked(article: Article) {
        val action = MainFragmentDirections.actionMainFragmentToNewsFragment(article)
        emitAction(MainAction.Navigate(action))
    }

    /**
     * Get intercepts errors from API request and displays error message.
     */
    fun showInterceptorErrors() = viewModelScope.launch {
        interceptorErrors.errorsInterceptor().collect {
            emitAction(MainAction.ShowMessage(it))
        }
    }
}
