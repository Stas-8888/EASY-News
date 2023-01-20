package com.example.newsapppp.presentation.ui.root

import com.example.newsapppp.R
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import com.example.newsapppp.presentation.extensions.launchCoroutine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
) : BaseViewModel<RootState>() {

    override val _state = MutableStateFlow<RootState>(RootState.Loading)
    override val state = _state.asStateFlow()

    init {
        onBottomNavClick()
    }

    private fun onBottomNavClick() = launchCoroutine{
        emitState(RootState.Navigation(R.id.mainFragment, R.id.saveFragment, R.id.searchFragment))
    }
}
