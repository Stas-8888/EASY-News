package com.example.newsapppp.presentation.screens.save

import androidx.navigation.NavDirections
import com.example.newsapppp.presentation.model.Article

sealed class SaveAction {
    data class Navigate(val navigateTo: NavDirections) : SaveAction()
    data class ShowDeleteDialog(val article: Article, val position: Int) : SaveAction()
}
