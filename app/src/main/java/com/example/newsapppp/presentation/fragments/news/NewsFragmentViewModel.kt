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

    private val _state = MutableStateFlow<SaveState>(SaveState.ShowLoading)
    val state: StateFlow<SaveState> = _state

    fun insert(article: Article) = viewModelScope.launch {
        insertArticleUseCase.insert(articleMapperToModel.convertToModel(article))
    }

    fun delete(article: Article) = viewModelScope.launch {
        deleteArticleUseCase.delete(articleMapperToModel.convertToModel(article))
    }

    fun saveFavorite(value: Boolean)= viewModelScope.launch {
        saveFavoriteUseCase.saveFavorite(value)
    }

    fun getFavorite(): Boolean  {
        return getFavoriteUseCase.getFavorite()
    }
}
