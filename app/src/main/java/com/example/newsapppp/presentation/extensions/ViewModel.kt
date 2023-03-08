package com.example.newsapppp.presentation.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun ViewModel.viewModeLaunch(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch() {
        block()
    }
}

suspend fun ViewModel.launchCoroutineIO(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(Dispatchers.IO) {
        block()
    }
}

fun <T> AppCompatActivity.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this) { t ->
        action(t)
    }
}

fun <T> Fragment.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(viewLifecycleOwner) { t ->
        action(t)
    }
}
