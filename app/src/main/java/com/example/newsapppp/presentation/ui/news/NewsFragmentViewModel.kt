package com.example.newsapppp.presentation.ui.news

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.interactors.DeleteArticleUseCase
import com.example.newsapppp.domain.interactors.GetFavoriteUseCase
import com.example.newsapppp.domain.interactors.InsertArticleUseCase
import com.example.newsapppp.domain.interactors.SaveFavoriteUseCase
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.ui.base.BaseViewModel
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
