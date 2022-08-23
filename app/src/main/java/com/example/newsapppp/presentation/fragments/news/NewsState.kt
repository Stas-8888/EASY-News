package com.example.newsapppp.presentation.fragments.news

sealed class NewsState {

    object ShowAsSavedTrue : NewsState()

    object ShowAsSavedFalse : NewsState()

    object ShowDelete : NewsState()

    object ShowInsert : NewsState()
}
