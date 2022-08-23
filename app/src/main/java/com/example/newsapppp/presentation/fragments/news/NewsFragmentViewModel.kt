package com.example.newsapppp.presentation.fragments.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.usecase.DeleteArticleUseCase
import com.example.newsapppp.domain.usecase.GetFavoriteUseCase
import com.example.newsapppp.domain.usecase.InsertArticleUseCase
import com.example.newsapppp.domain.usecase.SaveFavoriteUseCase
import com.example.newsapppp.presentation.fragments.SaveState
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
    private val getFavoriteUseCase: GetFavoriteUseCase
) : AndroidViewModel(application) {
    var  isFavorite = getFavorite()

    private val _state = MutableStateFlow<NewsSaveState>(NewsSaveState.ShowDelete)
    val state: StateFlow<NewsSaveState> = _state

    private fun insertArticle(article: Article) = viewModelScope.launch {
        insertArticleUseCase.insertArticle(articleMapperToModel.convertToModel(article))
    }

    private fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteArticleUseCase.deleteArticle(articleMapperToModel.convertToModel(article))
    }

    private fun saveFavorite(value: Boolean) = viewModelScope.launch {
        saveFavoriteUseCase.saveFavorite(value)
    }

    private fun getFavorite(): Boolean {
        return getFavoriteUseCase.getFavorite()
    }

    fun checkFavoriteIcon() = viewModelScope.launch {
        isFavorite = getFavorite()
        if (isFavorite) {
            _state.emit(NewsSaveState.ShowAsSavedFalse)
        } else {
            _state.emit(NewsSaveState.ShowAsSavedTrue)
        }
    }

    fun saveDeleteFavorite(article: Article) = viewModelScope.launch  {
        if (isFavorite) {
            saveFavorite(false)
            deleteArticle(article)
            _state.emit(NewsSaveState.ShowDelete)

        } else {
            saveFavorite(true)
            insertArticle(article)
            _state.emit(NewsSaveState.ShowInsert)
        }
    }
}
