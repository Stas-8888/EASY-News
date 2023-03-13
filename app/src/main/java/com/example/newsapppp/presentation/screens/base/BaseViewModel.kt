package com.example.newsapppp.presentation.screens.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Base class which represent ViewModel.
 * Where [S] - is State.
 * Where [A] - is Action.
 */
abstract class BaseViewModel<S, A> : ViewModel() {

    protected abstract val _state: MutableStateFlow<S>
    val state by lazy { _state.asStateFlow() }

    protected abstract val _shared: MutableSharedFlow<A>
    val shared by lazy { _shared.asSharedFlow() }

    /**
     * Emit new [S] state to [state] in Scope [viewModelScope].
     *
     * @param state - [S].
     */
    fun emit(state: S) = viewModelScope.launch {
        _state.emit(state)
    }

    /**
     * Emit new [A] action to [shared].
     *
     * @param action - [A].
     */
    fun emitShared(action: A) = viewModelScope.launch {
        _shared.emit(action)
    }
}
