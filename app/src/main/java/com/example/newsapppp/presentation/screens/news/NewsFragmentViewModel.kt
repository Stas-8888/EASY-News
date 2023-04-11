package com.example.newsapppp.presentation.screens.news

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.articles.cache.DeleteArticleUseCase
import com.example.newsapppp.domain.interactors.articles.cache.InsertArticleUseCase
import com.example.newsapppp.domain.interactors.sharedpreferences.GetFavoriteUseCase
import com.example.newsapppp.domain.interactors.sharedpreferences.SaveFavoriteUseCase
import com.example.newsapppp.presentation.extensions.isCurrentUserNull
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFragmentViewModel @Inject constructor(
    private val insertArticle: InsertArticleUseCase,
    private val deleteArticle: DeleteArticleUseCase,
    private val saveFavorite: SaveFavoriteUseCase,
    private val getFavorite: GetFavoriteUseCase,
    private val mapper: ArticleMapper
) : BaseViewModel<NewsState, NewsAction>() {

    private val favoriteIconSelected = R.drawable.ic_favorite
    private val favoriteIconUnselected = R.drawable.ic_favorite_border
    override val _state =
        MutableStateFlow<NewsState>(NewsState.ShowFavoriteIcon(favoriteIconUnselected))

    /**
     * Sets up the favorite icon in the UI.
     */
    fun setupFavoriteIcon(article: Article) = viewModelScope.launch {
        val icon = if (getFavorite(article.url)) favoriteIconSelected else favoriteIconUnselected
        emit(NewsState.ShowFavoriteIcon(icon))
    }

    /**
     * Handles click on the favorite icon by checking if the current user is null or not.
     * @param article the article to be marked as favorite or not
     */
    fun onFavoriteButtonClicked(article: Article) = when {
        isCurrentUserNull() -> emitAction(NewsAction.ShowMessage(R.string.error_registered))
        else -> toggleFavorite(article)
    }

    /**
     * Checks if the given [article] is already marked as favorite or not,
     * and performs the corresponding operations based on the result.
     * @param article the [Article] to check
     */
    private fun toggleFavorite(article: Article) = viewModelScope.launch {
        if (getFavorite(article.url).not()) insertFavorite(article) else deleteFavorite(article)
    }

    /**
     * Saves the given [article] to the favorites list and updates the UI accordingly.
     * @param article the [Article] to save as favorite
     */
    private fun insertFavorite(article: Article) = viewModelScope.launch {
        insertArticle(mapper.mapToModel(article))
        saveFavorite.invoke(article.url, true)
        emitAction(NewsAction.ShowFavoriteIcon(R.string.add_article, favoriteIconSelected))
    }

    /**
     * Deletes the given [article] from the favorites list and updates the UI accordingly.
     * @param article the [Article] to remove from favorites
     */
    private fun deleteFavorite(article: Article) = viewModelScope.launch {
        deleteArticle(mapper.mapToModel(article))
        saveFavorite.invoke(article.url, false)
        emitAction(NewsAction.ShowFavoriteIcon(R.string.delete_article, favoriteIconUnselected))
    }
}
