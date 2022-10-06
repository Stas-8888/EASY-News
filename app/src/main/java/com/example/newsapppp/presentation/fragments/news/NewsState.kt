package com.example.newsapppp.presentation.fragments.news

import com.example.newsapppp.presentation.fragments.base.State

sealed class NewsState: State {

    object ShowAsSaved : NewsState()

    object ShowUnSaved : NewsState()

}
