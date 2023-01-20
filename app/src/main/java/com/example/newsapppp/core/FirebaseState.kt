package com.example.newsapppp.core

sealed class FirebaseState<out T> {
    object Loading : FirebaseState<Nothing>()
    data class Success<out T>(val data: T) : FirebaseState<T>()
    data class Failure(val error: String) : FirebaseState<Nothing>()
    data class Navigate(val navigateTo: Int) : FirebaseState<Nothing>()

}
