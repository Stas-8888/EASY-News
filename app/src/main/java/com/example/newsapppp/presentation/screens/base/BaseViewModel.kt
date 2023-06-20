package com.example.newsapppp.presentation.screens.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapppp.common.network.NetworkHandlerRepository
import com.example.newsapppp.common.resources.ProvideResourcesRepository
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
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var networkHandler: NetworkHandlerRepository

    @Inject
    lateinit var provideResources: ProvideResourcesRepository

    protected abstract val _state: MutableStateFlow<S>
    val state by lazy { _state.asStateFlow() }

    private val _action: MutableSharedFlow<A> = MutableSharedFlow()
    val action by lazy { _action.asSharedFlow() }

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
