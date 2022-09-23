package com.example.newsapppp.presentation.fragments.news

sealed class NewsState {

    object ShowAsSaved : NewsState()

    object ShowUnSaved : NewsState()

}
