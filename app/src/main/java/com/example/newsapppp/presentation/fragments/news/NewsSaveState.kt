package com.example.newsapppp.presentation.fragments.news

sealed class NewsSaveState {

    object ShowAsSavedTrue : NewsSaveState()

    object ShowAsSavedFalse : NewsSaveState()

    object ShowDelete : NewsSaveState()

    object ShowInsert : NewsSaveState()
}
