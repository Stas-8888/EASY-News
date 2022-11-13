package com.example.newsapppp.presentation.ui.base

import androidx.lifecycle.ViewModel
import com.example.newsapppp.core.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<S : State> : ViewModel() {

    protected abstract val _state: MutableStateFlow<S>
    abstract val state: StateFlow<S>
}
