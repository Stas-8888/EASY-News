package com.example.newsapppp.presentation.ui.news

import com.example.newsapppp.core.State

sealed class NewsState: State {

    object ShowAsSaved : NewsState()

    object ShowUnSaved : NewsState()

    object Error : NewsState()

}
