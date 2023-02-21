package com.example.newsapppp.presentation.ui.save

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.articleLocalSource.DeleteAllUseCase
import com.example.newsapppp.domain.interactors.articleLocalSource.DeleteArticleUseCase
import com.example.newsapppp.domain.interactors.articleLocalSource.GetRoomArticleUseCase
import com.example.newsapppp.domain.interactors.preference.SaveFavoriteUseCase
import com.example.newsapppp.domain.repository.SharedPrefRepositoryContract
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SaveFragmentViewModel @Inject constructor(
    private val sharedPrefRepository: SharedPrefRepositoryContract,
    private val getRoomArticle: GetRoomArticleUseCase,
    private val deleteArticle: DeleteArticleUseCase,
    private val saveFavorite: SaveFavoriteUseCase,
    private val deleteAll: DeleteAllUseCase,
    private val mapper: ArticleMapper
) : BaseViewModel<SaveState, SaveAction>() {

    override val _state = MutableStateFlow<SaveState>(SaveState.ShowLoading)
    override val _shared = MutableSharedFlow<SaveAction>()

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
        deleteArticle(mapper.mapToModel(article))
        saveFavorite.saveFavorite(article.url, false)
    }

    fun onDeleteAllArticleClicked() = launchCoroutine {
        sharedPrefRepository.deleteAllFavorite()
        deleteAll(Unit)
    }

    fun onItemSwiped(article: Article, position: Int) {
        emitShared(SaveAction.ShowDeleteDialog(article, position))
    }

    fun onNewsAdapterItemClicked(article: Article) = launchCoroutine {
        emitShared(
            SaveAction.Navigate(
                SaveFragmentDirections.actionSaveFragmentToNewsFragment(
                    article
                )
            )
        )
    }
}
