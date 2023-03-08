package com.example.newsapppp.presentation.screens.favorite

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.localsource.DeleteAllUseCase
import com.example.newsapppp.domain.interactors.localsource.DeleteArticleUseCase
import com.example.newsapppp.domain.interactors.localsource.GetLocalArticleUseCase
import com.example.newsapppp.domain.interactors.preference.SaveFavoriteUseCase
import com.example.newsapppp.domain.repository.SharedPrefRepositoryContract
import com.example.newsapppp.presentation.extensions.viewModeLaunch
import com.example.newsapppp.presentation.mapper.ArticleMapper
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteFragmentViewModel @Inject constructor(
    private val sharedPref: SharedPrefRepositoryContract,
    private val getLocalArticle: GetLocalArticleUseCase,
    private val deleteArticle: DeleteArticleUseCase,
    private val saveFavorite: SaveFavoriteUseCase,
    private val deleteAll: DeleteAllUseCase,
    private val mapper: ArticleMapper
) : BaseViewModel<FavoriteState, FavoriteAction>() {

    override val _state = MutableStateFlow<FavoriteState>(FavoriteState.ShowLoading)
    override val _shared = MutableSharedFlow<FavoriteAction>()

    // Function to setup all news in the favorite list
    fun setupAllNews() = viewModeLaunch {
        getLocalArticle(Unit).collect {
            if (it.isNotEmpty()) {
                emit(
                    FavoriteState.ShowArticles(
                        articles = mapper.mapToListArticle(it),
                        progressBar = false,
                        state = false,
                        exception = null
                    )
                )
            } else {
                emit(
                    FavoriteState.ShowArticles(
                        articles = emptyList(),
                        progressBar = false,
                        state = true,
                        exception = R.string.empty_list
                    )
                )
            }
        }
    }

    // This function deletes item from the locally stored favorites.
    fun deleteArticle(article: Article) = viewModeLaunch {
        deleteArticle(mapper.mapToModel(article))
        saveFavorite.saveFavorite(article.url, false)
    }

    // This function deletes all article from the locally stored favorites.
    fun onDeleteAllClicked() = viewModeLaunch {
        getLocalArticle(Unit).collect {
            if (it.isNotEmpty()) {
                sharedPref.deleteAllFavorite()
                deleteAll(Unit)
            } else {
                emitShared(FavoriteAction.ShowMessage(R.string.empty_list))
            }
        }
    }

    // Function to handle the swipe gesture on a favorite list item
    fun onItemSwiped(article: Article, position: Int) {
        emitShared(FavoriteAction.ShowDeleteDialog(article, position))
    }

    // Function to handle the click event on a adapter item.
    fun onNewsAdapterItemClicked(article: Article) = viewModeLaunch {
        val action =  FavoriteFragmentDirections.actionFavoriteFragmentToNewsFragment(article)
        emitShared(FavoriteAction.Navigate(action))
    }
}
