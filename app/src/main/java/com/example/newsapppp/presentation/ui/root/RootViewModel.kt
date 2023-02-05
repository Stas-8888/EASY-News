package com.example.newsapppp.presentation.ui.root

import com.example.newsapppp.R
import com.example.newsapppp.core.interceptor.ErrorsInterceptorContract
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    var errors: ErrorsInterceptorContract
) : BaseViewModel<RootState>() {

    override val _state = MutableStateFlow<RootState>(
        RootState.Navigation(
            R.id.mainFragment,
            R.id.saveFragment,
            R.id.searchFragment
        )
    )
    override val state = _state.asStateFlow()

    fun interceptorErrors() {
        val error = errors.code200()
        if (error == "Data, Success received") {
            emit(RootState.InterceptorErrors(error))
        }
    }
}
