package com.example.newsapppp.presentation.screens.details

sealed class DetailsState {
    data class ShowFavoriteIcon(val favoriteIcon: Int) : DetailsState()
}
