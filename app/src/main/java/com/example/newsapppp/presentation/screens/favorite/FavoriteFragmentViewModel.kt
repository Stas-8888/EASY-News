package com.example.newsapppp.presentation.screens.favorite

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.articlecache.ArticleAllCacheUseCase
import com.example.newsapppp.domain.interactors.articlecache.DeleteAllUseCase
import com.example.newsapppp.domain.interactors.articlecache.DeleteArticleUseCase
import com.example.newsapppp.domain.interactors.sharedpreferences.SaveFavoriteUseCase
import com.example.newsapppp.domain.interactors.sharedpreferences.SharedPreferencesContract
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFragmentViewModel @Inject constructor(
    private val sharedPref: SharedPreferencesContract,
    private val articleAllCache: ArticleAllCacheUseCase,
    private val deleteArticle: DeleteArticleUseCase,
    private val saveFavorite: SaveFavoriteUseCase,
    private val deleteAll: DeleteAllUseCase,
    private val mapper: ArticleMapper
) : BaseViewModel<FavoriteState, FavoriteAction>() {

    override val _state = MutableStateFlow<FavoriteState>(FavoriteState.Loading)

    // Function to setup all news in the favorite list
    fun setupAllNews() = viewModelScope.launch {
        articleAllCache(Unit).collect { articles ->
            val data = if (articles.isNotEmpty()) {
                FavoriteState.ShowArticles(
                    articles = mapper.mapToListArticle(articles),
                    progressBar = false,
                    state = false,
                    exception = null
                )
            } else {
                FavoriteState.ShowArticles(
                    articles = emptyList(),
                    progressBar = false,
                    state = true,
                    exception = R.string.empty_list
                )
            }
            emit(data)
        }
    }

    // This function deletes item from the locally stored favorites.
    fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteArticle(mapper.mapToModel(article))
        saveFavorite.saveFavorite(article.url, false)
    }

    // This function deletes all article from the locally stored favorites.
    fun onDeleteAllClicked() = viewModelScope.launch {
        articleAllCache(Unit).collect {
            if (it.isNotEmpty()) {
                sharedPref.deleteAllFavorite()
                deleteAll(Unit)
            } else {
                emitAction(FavoriteAction.ShowMessage(R.string.empty_list))
            }
        }
    }

    // Function to handle the swipe gesture on a favorite list item
    fun onItemSwiped(article: Article, position: Int) {
        emitAction(FavoriteAction.ShowDeleteDialog(article, position))
    }

    // Function to handle the click event on a adapter item.
    fun onNewsAdapterItemClicked(article: Article) = viewModelScope.launch {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToNewsFragment(article)
        emitAction(FavoriteAction.Navigate(action))
    }
}
