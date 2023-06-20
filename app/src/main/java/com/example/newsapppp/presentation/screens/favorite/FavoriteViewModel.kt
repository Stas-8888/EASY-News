package com.example.newsapppp.presentation.screens.favorite

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.example.newsapppp.domain.use_case.articles.cache.ArticleAllCacheUseCase
import com.example.newsapppp.domain.use_case.articles.cache.DeleteAllUseCase
import com.example.newsapppp.domain.use_case.articles.cache.DeleteArticleUseCase
import com.example.newsapppp.domain.use_case.articles.cache.InsertArticleUseCase
import com.example.newsapppp.domain.use_case.sharedpreferences.SaveFavoriteUseCase
import com.example.newsapppp.domain.repository.SharedPreferencesRepository
import com.example.newsapppp.domain.model.ArticleModel
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val articleAllCache: ArticleAllCacheUseCase,
    private val sharedPref: SharedPreferencesRepository,
    private val deleteArticle: DeleteArticleUseCase,
    private val insertArticle: InsertArticleUseCase,
    private val saveFavorite: SaveFavoriteUseCase,
    private val deleteAll: DeleteAllUseCase,
    private val mapper: ArticleMapper
) : BaseViewModel<FavoriteState, FavoriteAction>() {

    override val _state = MutableStateFlow<FavoriteState>(FavoriteState.Loading)

    /**
     * Function setup all articles cache in the favorite list.
     */
    fun setupAllArticles() = viewModelScope.launch {
        articleAllCache(Unit).collect { articles ->
            emit(prepareArticlesData(articles))
        }
    }

    /**
     * Prepares the articles data for display in the favorite state.
     * @param articles the list of articles to be displayed
     * @return the favorite state containing the articles, progress bar state, and exception (if any)
     */
    private fun prepareArticlesData(articles: List<ArticleModel>): FavoriteState {
        return if (articles.isNotEmpty()) {
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
    }

    /**
     * This function deletes item from the locally stored favorites.
     */
    fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteArticle(mapper.mapToModel(article))
        saveFavorite.invoke(article.url, false)
    }

    /**
     * Called when the "Delete All" button is clicked. Checks if the list of all cached articles is
     * empty, and if so, displays a message to the user. If the list is not empty, shows a dialog asking
     * the user to confirm the deletion of all articles.
     */
    fun onDeleteAllClicked() = viewModelScope.launch {
        if (articleAllCache(Unit).first().isEmpty()) {
            emitAction(FavoriteAction.ShowMessage(R.string.empty_list))
        } else {
            emitAction(FavoriteAction.ShowDeleteAllDialog)
        }
    }

    /**
     * Called when the user confirms the deletion of all articles. Deletes all articles from the
     * local database and clears the shared preferences for favorite articles.
     */
    fun onSuccessDeleteDialogAction() = viewModelScope.launch {
        sharedPref.deleteAllFavorite()
        deleteAll(Unit)
    }

    /**
     * Function to handle the swipe gesture on a favorite list item.
     */
    fun onItemSwiped(article: Article, position: Int) {
        emitAction(FavoriteAction.ShowItemDeleteDialog(article, position))
    }

    /**
     * Function to handle the click event on a adapter item.
     */
    fun onNewsAdapterItemClicked(article: Article) = viewModelScope.launch {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailsFragment(article)
        emitAction(FavoriteAction.Navigate(action))
    }

    /**
     * Function update Articles when move it.
     */
    fun onMoveUpdateArticle(article: Article) = viewModelScope.launch {
        insertArticle(mapper.mapToModel(article))
    }
}
