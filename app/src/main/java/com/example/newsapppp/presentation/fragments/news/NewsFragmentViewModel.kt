package com.example.newsapppp.presentation.fragments.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.usecase.DeleteArticleUseCase
import com.example.newsapppp.domain.usecase.GetFavoriteUseCase
import com.example.newsapppp.domain.usecase.InsertArticleUseCase
import com.example.newsapppp.domain.usecase.SaveFavoriteUseCase
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import com.example.newsapppp.presentation.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFragmentViewModel @Inject constructor(
    application: Application,
    private val insertArticleUseCase: InsertArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val articleMapperToModel: ArticleMapperToModel,
    private val saveFavoriteUseCase: SaveFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
) : AndroidViewModel(application) {
    private var isFavorite = false

    private val _state = MutableStateFlow<NewsState>(NewsState.ShowAsSaved)
    val state: StateFlow<NewsState> = _state

    private fun insertArticle(article: Article) = viewModelScope.launch {
        insertArticleUseCase.insertArticle(articleMapperToModel.convertToModel(article))
    }

    private fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteArticleUseCase.deleteArticle(articleMapperToModel.convertToModel(article))
    }

    private fun saveFavorite(key: String, value: Boolean) = viewModelScope.launch {
        saveFavoriteUseCase.saveFavorite(key, value)
    }

    private suspend fun getFavorite(key: String): Boolean {
        return getFavoriteUseCase.getFavorite(key)
    }

    fun checkFavoriteIcon(article: Article) = viewModelScope.launch {
        if (isFavorite != getFavorite(article.url)) {
            _state.emit(NewsState.ShowUnSaved)
        } else {
            _state.emit(NewsState.ShowAsSaved)
        }
    }

    fun saveDeleteFavorite(article: Article) = viewModelScope.launch {
        if (isFavorite == getFavorite(article.url)) {
            insertArticle(article)
            saveFavorite(article.url, true)
            _state.emit(NewsState.ShowAsSaved)

        } else {
            deleteArticle(article)
            saveFavorite(article.url, false)
            _state.emit(NewsState.ShowUnSaved)
        }
    }
}
