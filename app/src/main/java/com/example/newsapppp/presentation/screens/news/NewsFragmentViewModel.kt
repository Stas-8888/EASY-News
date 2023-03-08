package com.example.newsapppp.presentation.screens.news

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.localsource.DeleteArticleUseCase
import com.example.newsapppp.domain.interactors.localsource.InsertArticleUseCase
import com.example.newsapppp.domain.interactors.preference.GetFavoriteUseCase
import com.example.newsapppp.domain.interactors.preference.SaveFavoriteUseCase
import com.example.newsapppp.presentation.extensions.viewModeLaunch
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NewsFragmentViewModel @Inject constructor(
    private val insertArticle: InsertArticleUseCase,
    private val deleteArticle: DeleteArticleUseCase,
    private val saveFavorite: SaveFavoriteUseCase,
    private val mapper: ArticleMapper,
    private val getFavorite: GetFavoriteUseCase,
    private var firebaseAuth: FirebaseAuth
) : BaseViewModel<NewsState, NewsAction>() {

    private var isFavorite = false
    private val favoriteIconSelected = R.drawable.ic_favorite
    private val favoriteIconUnselected = R.drawable.ic_favorite_border
    override val _state =
        MutableStateFlow<NewsState>(NewsState.ShowFavoriteIcon(favoriteIconUnselected))
    override val _shared = MutableSharedFlow<NewsAction>()

    fun setupFavoriteIcon(article: Article) = viewModeLaunch {
        val icon = if (isFavorite != getFavorite(article.url))
            favoriteIconSelected else favoriteIconUnselected
        emit(NewsState.ShowFavoriteIcon(icon))
    }

    fun onFavoriteIconClicked(article: Article) = viewModeLaunch {
        if (firebaseAuth.currentUser != null) {
            if (isFavorite == getFavorite(article.url)) {
                insertArticle(mapper.mapToModel(article))
                saveFavorite.saveFavorite(article.url, true)
                emitShared(NewsAction.ShowFavoriteIcon(R.string.add_article, favoriteIconSelected))
            } else {
                deleteArticle(mapper.mapToModel(article))
                saveFavorite.saveFavorite(article.url, false)
                emitShared(NewsAction.ShowFavoriteIcon(R.string.delete_article, favoriteIconUnselected))
            }
        } else {
            emitShared(NewsAction.ShowMessage(R.string.error_registered))
        }
    }
}
