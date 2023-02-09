package com.example.newsapppp.presentation.ui.save

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.articleLocalSource.DeleteAllUseCase
import com.example.newsapppp.domain.interactors.articleLocalSource.DeleteArticleUseCase
import com.example.newsapppp.domain.interactors.articleLocalSource.GetRoomArticleUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.mapper.ArticleMapper
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
    private val mapper: ArticleMapper
) : BaseViewModel<SaveState>() {

    override val _state = MutableStateFlow<SaveState>(SaveState.ShowLoading)
    override val state = _state.asStateFlow()

    fun setupAllNews() = launchCoroutine {
        getRoomArticle(Unit).collect {
            if (it.isNotEmpty()) {
                emit(
                    SaveState.ShowArticles(
                        articles = mapper.mapToListArticle(it),
                        progressBar = false,
                        state = false,
                        exception = null
                    )
                )
            } else {
                emit(
                    SaveState.ShowArticles(
                        articles = emptyList(),
                        progressBar = false,
                        state = true,
                        exception = R.string.empty_list
                    )
                )
            }
        }
    }

    fun deleteArticle(article: Article) = launchCoroutine {
        deleteArticleUseCase(mapper.mapToModel(article))
    }

    fun onDeleteAllArticleClicked() = launchCoroutine {
        deleteAll(Unit)
    }

    fun onItemSwiped(article: Article, position: Int) {
        emit(SaveState.ShowDeleteDialog(article, position))
    }
}
