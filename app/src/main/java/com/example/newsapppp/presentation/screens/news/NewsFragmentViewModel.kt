package com.example.newsapppp.presentation.screens.news

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.articlecache.DeleteArticleUseCase
import com.example.newsapppp.domain.interactors.articlecache.InsertArticleUseCase
import com.example.newsapppp.domain.interactors.sharedpreferences.GetFavoriteUseCase
import com.example.newsapppp.domain.interactors.sharedpreferences.SaveFavoriteUseCase
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
    private val mapper: ArticleMapper,
    private val getFavorite: GetFavoriteUseCase,
) : BaseViewModel<NewsState, NewsAction>() {

    private var isFavorite = false
    private val favoriteIconSelected = R.drawable.ic_favorite
    private val favoriteIconUnselected = R.drawable.ic_favorite_border
    override val _state =
        MutableStateFlow<NewsState>(NewsState.ShowFavoriteIcon(favoriteIconUnselected))

    // Sets up the favorite icon in the UI
    fun setupFavoriteIcon(article: Article) = viewModelScope.launch {
        val icon = if (isFavorite != getFavorite(article.url))
            favoriteIconSelected else favoriteIconUnselected
        emit(NewsState.ShowFavoriteIcon(icon))
    }

    // Handles click on favorite icon
    fun onFavoriteButtonClicked(article: Article) = viewModelScope.launch {
        when {
            isCurrentUserNull -> emitAction(NewsAction.ShowMessage(R.string.error_registered))
            else -> try {
                if (isFavorite == getFavorite(article.url)) {
                    insertArticle(mapper.mapToModel(article))
                    saveFavorite.saveFavorite(article.url, true)
                    emitAction(
                        NewsAction.ShowFavoriteIcon(R.string.add_article, favoriteIconSelected)
                    )
                } else {
                    deleteArticle(mapper.mapToModel(article))
                    saveFavorite.saveFavorite(article.url, false)
                    emitAction(
                        NewsAction.ShowFavoriteIcon(R.string.delete_article, favoriteIconUnselected)
                    )
                }
            } catch (e: Exception) {
                emitAction(NewsAction.ShowMessage(R.string.error))
            }
        }
    }
}
