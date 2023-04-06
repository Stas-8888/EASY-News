package com.example.newsapppp.presentation.screens.favorite

import androidx.navigation.NavDirections
import com.example.newsapppp.presentation.model.Article

sealed class FavoriteAction {
    data class Navigate(val navigateTo: NavDirections) : FavoriteAction()
    data class ShowItemDeleteDialog(val article: Article, val position: Int) : FavoriteAction()
    object ShowDeleteAllDialog : FavoriteAction()
    data class ShowMessage(val message: Int) : FavoriteAction()
}
