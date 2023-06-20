package com.example.newsapppp.presentation.screens.details

sealed class DetailsAction {
    data class ShowMessage(val message: Int) : DetailsAction()
    data class ShowFavoriteIcon(val status: Int, val favoriteIcon: Int) : DetailsAction()
}
