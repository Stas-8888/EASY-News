package com.example.newsapppp.presentation.ui.save

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.articleLocalSource.DeleteAllUseCase
import com.example.newsapppp.domain.interactors.articleLocalSource.DeleteArticleUseCase
import com.example.newsapppp.domain.interactors.articleLocalSource.GetRoomArticleUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.mapper.ArticleMapperToModel
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SaveFragmentViewModel @Inject constructor(
    private val getRoomArticle: GetRoomArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val deleteAll: DeleteAllUseCase,
    private val articleMapperToModel: ArticleMapperToModel
) : BaseViewModel<SaveState>() {

    override val _state = MutableStateFlow<SaveState>(SaveState.ShowLoading)
    override val state = _state.asStateFlow()

    fun setupAllNews() = launchCoroutine {
        try {
            getRoomArticle(Unit).collect {
                _state.emit(SaveState.ShowArticles(articleMapperToModel.articleToModelArticle(it)))
            }
        } catch (e: Exception) {
            emitState(SaveState.Error(R.string.error))
        }
    }

    fun deleteArticle(article: Article) = launchCoroutine {
        deleteArticleUseCase(articleMapperToModel.mapFromEntity(article))
    }

    fun deleteAllArticle() = launchCoroutine {
        deleteAll(Unit)
    }

    fun onItemSwiped(article: Article, position: Int) {
        emitState(SaveState.ShowDeleteDialog(article, position))
    }
}
