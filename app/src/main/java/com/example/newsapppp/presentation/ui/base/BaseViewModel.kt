package com.example.newsapppp.presentation.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel<State> : ViewModel() {

    protected abstract val _state: MutableSharedFlow<State>
    abstract val state: SharedFlow<State>

    suspend fun emit(action: State) {
        _state.emit(action)
    }
}
