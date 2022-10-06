package com.example.newsapppp.presentation.fragments.save

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.usecase.DeleteAllUseCase
import com.example.newsapppp.domain.usecase.DeleteArticleUseCase
import com.example.newsapppp.domain.usecase.GetRoomArticleUseCase
import com.example.newsapppp.presentation.fragments.base.BaseViewModel
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import com.example.newsapppp.presentation.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveFragmentViewModel @Inject constructor(
    private val getRoomArticleUseCase: GetRoomArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val deleteAllUseCase: DeleteAllUseCase,
    private val articleMapperToModel: ArticleMapperToModel
) : BaseViewModel<SaveState>() {

    override val _state = MutableStateFlow<SaveState>(SaveState.ShowLoading)
    override val state = _state.asStateFlow()

    fun getAllNews() = viewModelScope.launch {
        _state.emit(SaveState.ShowLoading)
        getRoomArticleUseCase.getAllArticles().collect {
            _state.emit(SaveState.ShowArticles(articleMapperToModel.articleToModelArticle(it)))
        }
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteArticleUseCase.deleteArticle(articleMapperToModel.convertToModel(article))
    }

    fun deleteAllArticle() = viewModelScope.launch {
        deleteAllUseCase.deleteAllArticle()
    }
}
