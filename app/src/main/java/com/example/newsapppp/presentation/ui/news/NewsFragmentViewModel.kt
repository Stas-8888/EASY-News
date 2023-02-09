package com.example.newsapppp.presentation.ui.news

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.articleLocalSource.DeleteArticleUseCase
import com.example.newsapppp.domain.interactors.articleLocalSource.InsertArticleUseCase
import com.example.newsapppp.domain.interactors.preference.GetFavoriteUseCase
import com.example.newsapppp.domain.interactors.preference.SaveFavoriteUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NewsFragmentViewModel @Inject constructor(
    private val insertArticle: InsertArticleUseCase,
    private val deleteArticle: DeleteArticleUseCase,
    private val saveFavorite: SaveFavoriteUseCase,
    private val mapper: ArticleMapper,
    private val getFavorite: GetFavoriteUseCase,
    private var firebaseAuth: FirebaseAuth
) : BaseViewModel<NewsState>() {

    private var isFavorite = false
    private val favoritesIconSelected = R.drawable.ic_favorite
    private val favoritesIconUnselected = R.drawable.ic_favorite_border
    override val _state =
        MutableStateFlow<NewsState>(NewsState.HideFavoriteIcon(favoritesIconUnselected))
    override val state = _state.asStateFlow()

    fun setupFavoriteIcon(article: Article) = launchCoroutine {
        if (isFavorite != getFavorite(article.url)) {
            _state.emit(NewsState.ShowFavoriteIcon(favoritesIconSelected))
        } else {
            _state.emit(NewsState.HideFavoriteIcon(favoritesIconUnselected))
        }
    }

    fun onFavoriteIconClicked(article: Article) = launchCoroutine {
        if (firebaseAuth.currentUser != null) {
            if (isFavorite == getFavorite(article.url)) {
                insertArticle(mapper.mapToModel(article))
                saveFavorite.saveFavorite(article.url, true)
                emit(NewsState.SaveFavorite(R.string.Add_Article, favoritesIconSelected))
            } else {
                deleteArticle(mapper.mapToModel(article))
                saveFavorite.saveFavorite(article.url, false)
                emit(NewsState.DeleteFavorite(R.string.Delete_Article, favoritesIconUnselected))
            }
        } else {
            emit(NewsState.Error(R.string.error_registered))
        }
    }
}
