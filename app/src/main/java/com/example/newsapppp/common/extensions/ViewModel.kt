package com.example.newsapppp.common.extensions

import com.example.newsapppp.presentation.screens.base.BaseViewModel

/**
 * Returns a string representation of the resource identified by the given id using provideResources.
 * @param id the id of the resource to retrieve
 * @return a string representation of the resource
 */
fun <S, A> BaseViewModel<S, A>.makeString(id: Int): String {
    return provideResources.makeString(id)
}

/**
 * Checks if the device is currently offline by calling isNetworkAvailable() on networkHandler.
 * @return true if the device is offline, false if it is online
 */
fun <S, A> BaseViewModel<S, A>.isOffline(): Boolean {
    return networkHandler.isNetworkAvailable().not()
}

/**
 * Checks if the current user of the Firebase authentication service is null or not, and whether their email has been verified.
 * @return true if the user is null or their email has not been verified, false otherwise
 */
fun <S, A> BaseViewModel<S, A>.isCurrentUserNull(): Boolean {
    return firebaseAuth.currentUser?.isEmailVerified == null
}
