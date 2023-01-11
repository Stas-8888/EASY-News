package com.example.newsapppp.presentation.utils.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun ViewModel.launchCoroutine(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch() {
        block()
    }
}

suspend fun ViewModel.launchCoroutineIO(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.IO) {
        block()
    }
}
