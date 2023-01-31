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
    private val insertArticleUseCase: InsertArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val articleMapper: ArticleMapper,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private var firebaseAuth: FirebaseAuth
) : BaseViewModel<NewsState>() {

    private var isFavorite = false
    private val favoritesIconSelected = R.drawable.ic_favorite
    private val favoritesIconUnselected = R.drawable.ic_favorite_border
    override val _state = MutableStateFlow<NewsState>(NewsState.HideFavoriteIcon(favoritesIconUnselected))
    override val state = _state.asStateFlow()

    fun setupFavoriteIcon(article: Article) = launchCoroutine {
        if (isFavorite != getFavoriteUseCase(article.url)) {
            _state.emit(NewsState.ShowFavoriteIcon(favoritesIconSelected))
        } else {
            _state.emit(NewsState.HideFavoriteIcon(favoritesIconUnselected))
        }
    }

    fun onFavoriteIconClicked(article: Article) = launchCoroutine {
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            if (isFavorite == getFavoriteUseCase(article.url)) {
                emit(
                    NewsState.SaveFavoriteArticle(
                        insertArticleUseCase(articleMapper.mapToModel(article)),
                        saveFavoriteUseCase.saveFavorite(article.url, true),
                        R.string.Add_Article,
                        favoritesIconSelected
                    )
                )
            } else {
                emit(
                    NewsState.DeleteFavoriteArticle(
                        deleteArticleUseCase(articleMapper.mapToModel(article)),
                        saveFavoriteUseCase.saveFavorite(article.url, false),
                        R.string.Delete_Article,
                        favoritesIconUnselected
                    )
                )
            }
        } else {
            emit(NewsState.Error(R.string.error_registered))
        }
    }
}
