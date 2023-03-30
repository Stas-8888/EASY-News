package com.example.newsapppp.presentation.screens.main

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.newsapppp.R
import com.example.newsapppp.data.remote.interceptor.ErrorsInterceptorContract
import com.example.newsapppp.domain.interactors.articleremote.FetchedArticlesUseCase
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
    fun fetchAndShowArticles(tab: TabLayout.Tab? = null) = viewModelScope.launch {
        when {
            isOffline() -> emitAction(MainAction.ShowNetworkDialog(R.string.internet_disconnected))
            else -> try {
                val category = tab?.let { categories[it.position] } ?: categories.first()
                val news = fetchedArticles(category).cachedIn(viewModelScope)
                news.collect { articles ->
                    val filteredArticles = articles.filter { article ->
                        article.urlToImage != null && article.title != null
                    }
                    val mappedArticles = mapper.mapToPagingArticle(filteredArticles)
                    val countryFlag = getCountryFlag(Unit)
                    emit(MainState.ShowUI(mappedArticles, countryFlag))
                }
            } catch (e: Exception) {
                emitAction(MainAction.ShowMessage(R.string.error))
            }
        }
    }

    /**
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
        val errors = interceptorErrors.errorsInterceptor()
        errors.collect {
            emitAction(MainAction.ShowMessage(it))
        }
    }
}
