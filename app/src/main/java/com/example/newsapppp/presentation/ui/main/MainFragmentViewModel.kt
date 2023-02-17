package com.example.newsapppp.presentation.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.newsapppp.data.network.interceptor.ErrorsInterceptorContract
import com.example.newsapppp.domain.interactors.articleRemote.GetNewsUseCase
import com.example.newsapppp.domain.interactors.preference.GetCountryFlagUseCase
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
class MainFragmentViewModel @Inject constructor(
    private val getNews: GetNewsUseCase,
    private val mapper: ArticleMapper,
    private val getCountryFlag: GetCountryFlagUseCase,
    private val interceptorErrors: ErrorsInterceptorContract
) : BaseViewModel<MainState, MainAction>() {

    override val _state = MutableStateFlow<MainState>(MainState.ShowLoading)
    override val state = _state.asStateFlow()

    override val _shared = MutableSharedFlow<MainAction>()
    override val shared = _shared.asSharedFlow()

    fun setupUi(category: String) = launchCoroutine {
        getNews(category).cachedIn(viewModelScope).collect() {
            val data = it.filter { article -> article.urlToImage != null && article.title != null }
            emit(
                MainState.SetupUi(
                    article = mapper.mapToPagingArticle(data),
                    countryFlag = getCountryFlag(Unit)
                )
            )
        }
    }

    fun interceptorErrors() = launchCoroutine {
        interceptorErrors.errorsInterceptor().collect() {
            if (it.isNotEmpty()) {
                emitShared(MainAction.Message(it))
            }
        }
    }

    fun showOrHideFloatButton(getFirstNewsPosition: Int) {
        if (getFirstNewsPosition < 1)
            emit(MainState.BottomVisibility(false)) else emit(MainState.BottomVisibility(true))
    }

    fun onNewsAdapterClicked(article: Article) {
        emitShared(
            MainAction.Navigate(
                MainFragmentDirections.actionMainFragmentToNewsFragment(
                    article
                )
            )
        )
    }

    fun onBtSettingsClicked() {
        emitShared(
            MainAction.Navigate(MainFragmentDirections.actionMainFragmentToSettingsFragment()))
    }
}
