package com.example.newsapppp.presentation.screens.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.core.network.NetworkHandlerContract
import com.example.newsapppp.core.resources.ProvideResourcesContract
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Base class which represent ViewModel.
 * Where [S] - is State.
 * Where [A] - is Action.
 */

abstract class BaseViewModel<S, A> : ViewModel() {

    @Inject
    lateinit var networkHandler: NetworkHandlerContract

    @Inject
    lateinit var provideResources: ProvideResourcesContract

    protected abstract val _state: MutableStateFlow<S>
    val state by lazy { _state.asStateFlow() }

    private val _action: MutableSharedFlow<A> = MutableSharedFlow()
    val action by lazy { _action.asSharedFlow() }

    protected val isCurrentUserNull by lazy { FirebaseAuth.getInstance().currentUser == null }

    /**
     * Emit new [S] state to [state] in Scope [viewModelScope].
     *
     * @param state - [S].
     */
    fun emit(state: S) = viewModelScope.launch {
        _state.emit(state)
    }

    /**
     * Emit new [A] action to [action].
     *
     * @param action - [A].
     */
    fun emitAction(action: A) = viewModelScope.launch {
        _action.emit(action)
    }
}
