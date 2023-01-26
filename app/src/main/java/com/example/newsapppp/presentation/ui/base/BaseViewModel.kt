package com.example.newsapppp.presentation.ui.base

import androidx.lifecycle.ViewModel
import com.example.newsapppp.presentation.extensions.launchCoroutine
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel<State> : ViewModel() {

    protected abstract val _state: MutableSharedFlow<State>
    abstract val state: SharedFlow<State>

    fun emitState(action: State) = launchCoroutine{
        _state.emit(action)
    }
}
