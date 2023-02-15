package com.example.newsapppp.presentation.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.presentation.extensions.launchCoroutine
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * Base class which represent ViewModel.
 * Where [S] - is State.
 * Where [A] - is State.
 */

abstract class BaseViewModel<S, A> : ViewModel() {

    protected abstract val _state: MutableSharedFlow<S>
    abstract val state: SharedFlow<S>

    protected abstract val _shared: MutableSharedFlow<A>
    abstract val shared: SharedFlow<A>

    /**
     * Emit new [S] state to [stateFlow] in Scope [viewModelScope].
     *
     * @param state - [S].
     */
    fun emit(state: S) = launchCoroutine {
        _state.emit(state)
    }

    /**
     * Emit new [A] action to [sharedFlow].
     *
     * @param action - [A].
     */
    fun emitShared(action: A) = launchCoroutine {
        _shared.emit(action)
    }
}
