package com.example.newsapppp.presentation.extensions

import com.example.newsapppp.presentation.screens.base.BaseViewModel

fun <S, A> BaseViewModel<S, A>.makeString(id: Int): String {
    return provideResources.makeString(id)
}

fun <S, A> BaseViewModel<S, A>.isOffline(): Boolean {
    return networkHandler.isNetworkAvailable().not()
}

fun <S, A> BaseViewModel<S, A>.isCurrentUserNull(): Boolean {
    return firebaseAuth.currentUser?.isEmailVerified == null
}
