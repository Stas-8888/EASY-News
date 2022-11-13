package com.example.newsapppp.presentation.ui.save

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.interactors.DeleteAllUseCase
import com.example.newsapppp.domain.interactors.DeleteArticleUseCase
import com.example.newsapppp.domain.interactors.GetRoomArticleUseCase
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.ui.base.BaseViewModel
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
        getRoomArticleUseCase(Unit).collect {
            _state.emit(SaveState.ShowArticles(articleMapperToModel.articleToModelArticle(it)))
        }
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteArticleUseCase(articleMapperToModel.mapFromEntity(article))
    }

    fun deleteAllArticle() = viewModelScope.launch {
        deleteAllUseCase(Unit)
    }
}
