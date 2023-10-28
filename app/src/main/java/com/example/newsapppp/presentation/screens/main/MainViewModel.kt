package com.example.newsapppp.presentation.screens.main

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newsapppp.R
import com.example.newsapppp.common.Constants.ONE
import com.example.newsapppp.data.interceptor.ErrorsInterceptorRepository
import com.example.newsapppp.domain.use_case.articles.remote.FetchedArticlesUseCase
import com.example.newsapppp.domain.use_case.shared_preferences.GetCountryFlagUseCase
import com.example.newsapppp.presentation.extensions.isOffline
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mapper: ArticleMapper,
    private val getCountryFlag: GetCountryFlagUseCase,
    private val fetchedArticles: FetchedArticlesUseCase,
    private val interceptorErrors: ErrorsInterceptorRepository
) : BaseViewModel<MainState, MainAction>() {

    override val _state = MutableStateFlow<MainState>(MainState.BottomVisibility(false))

    /**
     * Sets up UI for main fragment with news for first category.
     * Sets up news for a specific tab/category.
     */
    fun showArticles(category: String? = null) = when {
        isOffline() -> emitAction(MainAction.ShowNetworkDialog(R.string.internet_disconnected))
        else -> fetchAndEmitArticles(category ?: "General")
    }

    /**
     * Fetches articles for the specified category and emits the UI state to display them.
     * If no tab is provided, fetches articles for the first category.
     * @param category the tab representing the category of news to fetch and display
     */
    private fun fetchAndEmitArticles(category: String) = viewModelScope.launch {
        val articles = fetchedArticles(category).cachedIn(viewModelScope).first()
        val mappedArticles = mapper.mapToPagingArticle(articles)
        emit(MainState.ShowUI(mappedArticles, getCountryFlag(Unit)))


//        try {
//            emit(MainState.ShowUI(mappedArticles, getCountryFlag(Unit)))
//        } catch (e: Exception) {
//            emitAction(MainAction.ShowError(R.string.error))
//        }
    }

    /**‹
     * Shows/hides float button based on position of first news article.
     */
    fun showOrHideFloatButton(getFirstNewsPosition: Int) {
        val isVisible = getFirstNewsPosition >= ONE
        emit(MainState.BottomVisibility(isVisible))
    }

    /**
     * Handles click on settings button.
     */
    fun onBtSettingsClicked() {
        val action = MainFragmentDirections.actionMainFragmentToSettingsFragment()
        emitAction(MainAction.OnClicked(action))
    }

    /**
     * Handles click on news article in recycler view.
     */
    fun onArticleItemClicked(article: Article) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(article)
        emitAction(MainAction.OnClicked(action))
    }

    /**
     * Get intercepts errors from API request and displays error message.
     */
    fun showInterceptorErrors() = viewModelScope.launch {
        interceptorErrors.errorsInterceptor().collect {
            emitAction(MainAction.ShowError(it))
        }
    }
}
