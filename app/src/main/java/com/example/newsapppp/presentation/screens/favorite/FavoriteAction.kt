package com.example.newsapppp.presentation.screens.favorite

import androidx.navigation.NavDirections
import com.example.newsapppp.presentation.model.Article

sealed class FavoriteAction {
    data class Navigate(val navigateTo: NavDirections) : FavoriteAction()
    data class ShowDeleteDialog(val article: Article, val position: Int) : FavoriteAction()
    data class ShowMessage(val message: Int) : FavoriteAction()
}
