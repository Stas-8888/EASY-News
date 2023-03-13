package com.example.newsapppp.presentation.screens.main

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.newsapppp.data.remote.interceptor.ErrorsInterceptorContract
import com.example.newsapppp.domain.interactors.articleremote.GetNewsUseCase
import com.example.newsapppp.domain.interactors.preference.GetCountryFlagUseCase
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val getNews: GetNewsUseCase,
    private val mapper: ArticleMapper,
    private val getCountryFlag: GetCountryFlagUseCase,
    private val interceptorErrors: ErrorsInterceptorContract
) : BaseViewModel<MainState, MainAction>() {

    override val _state = MutableStateFlow<MainState>(MainState.ShowLoading)
    override val _shared = MutableSharedFlow<MainAction>()

    private val categories = listOf(
        "Technology", "Sports", "Science", "Entertainment", "Business", "Health"
    )

    // Sets up UI for main fragment with news for first category
    fun setupUi() = viewModelScope.launch {
        val news = getNews(categories.first()).cachedIn(viewModelScope)
        news.collect { articles ->
            val filteredArticles = articles.filter { article ->
                article.urlToImage != null && article.title != null
            }
            val mappedArticles = mapper.mapToPagingArticle(filteredArticles)
            val countryFlag = getCountryFlag(Unit)
            val uiState = MainState.ShowUI(mappedArticles, countryFlag)
            emit(uiState)
        }
    }

    // Sets up news for a specific tab/category
    fun setupTabLayout(tab: TabLayout.Tab) = viewModelScope.launch {
        val category = categories[tab.position]
        val news = getNews(category).cachedIn(viewModelScope)
        news.collect { articles ->
            val filteredArticles = articles.filter { it.urlToImage != null && it.title != null }
            val mappedArticles = mapper.mapToPagingArticle(filteredArticles)
            val countryFlag = getCountryFlag(Unit)
            emit(MainState.ShowUI(mappedArticles, countryFlag))
        }
    }

    // Shows/hides float button based on position of first news article
    fun showOrHideFloatButton(getFirstNewsPosition: Int) {
        val isVisible = getFirstNewsPosition >= 1
        emit(MainState.BottomVisibility(isVisible))
    }

    // Handles click on settings button
    fun onBtSettingsClicked() {
        val action = MainFragmentDirections.actionMainFragmentToSettingsFragment()
        emitShared(MainAction.Navigate(action))
    }

    // Handles click on news article in recycler view
    fun onNewsAdapterClicked(article: Article) {
        val action = MainFragmentDirections.actionMainFragmentToNewsFragment(article)
        emitShared(MainAction.Navigate(action))
    }

    // Intercepts errors from API request and displays error message
    fun interceptorErrors() = viewModelScope.launch {
        val errors = interceptorErrors.errorsInterceptor()
        errors.filter { it.isNotEmpty() }.collect {
            emitShared(MainAction.ShowMessage(it))
        }
    }
}
