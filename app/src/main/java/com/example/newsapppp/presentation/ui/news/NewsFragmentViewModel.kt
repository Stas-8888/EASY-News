package com.example.newsapppp.presentation.ui.news

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.interactors.room.DeleteArticleUseCase
import com.example.newsapppp.domain.interactors.preference.GetFavoriteUseCase
import com.example.newsapppp.domain.interactors.room.InsertArticleUseCase
import com.example.newsapppp.domain.interactors.preference.SaveFavoriteUseCase
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFragmentViewModel @Inject constructor(
    private val insertArticleUseCase: InsertArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val articleMapperToModel: ArticleMapperToModel,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase
) : BaseViewModel<NewsState>() {

    private var isFavorite = false
    private lateinit var firebaseAuth: FirebaseAuth


    override val _state = MutableStateFlow<NewsState>(NewsState.ShowAsSaved)
    override val state = _state.asStateFlow()

    private suspend fun insertArticle(article: Article) {
        insertArticleUseCase(articleMapperToModel.mapFromEntity(article))
    }

    private suspend fun deleteArticle(article: Article) {
        deleteArticleUseCase(articleMapperToModel.mapFromEntity(article))
    }

    private fun saveFavorite(key: String, value: Boolean) = viewModelScope.launch {
        saveFavoriteUseCase.saveFavorite(key, value)
    }

    private suspend fun getFavorite(key: String): Boolean {
        return getFavoriteUseCase(key)
    }

    fun checkFavoriteIcon(article: Article) = viewModelScope.launch {
        if (isFavorite != getFavorite(article.url)) {
            _state.emit(NewsState.ShowUnSaved)
        } else {
            _state.emit(NewsState.ShowAsSaved)
        }
    }

    fun saveDeleteFavorite(article: Article) = viewModelScope.launch {
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            if (isFavorite == getFavorite(article.url)) {
                insertArticle(article)
                saveFavorite(article.url, true)
                _state.emit(NewsState.ShowAsSaved)

            } else {
                deleteArticle(article)
                saveFavorite(article.url, false)
                _state.emit(NewsState.ShowUnSaved)
            }
        } else {
            _state.emit(NewsState.Error)
        }

    }
}
