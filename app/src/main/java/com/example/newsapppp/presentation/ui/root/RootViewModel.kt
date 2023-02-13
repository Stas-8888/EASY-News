package com.example.newsapppp.presentation.ui.root

import com.example.newsapppp.R
import com.example.newsapppp.data.network.interceptor.ErrorsInterceptorContract
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val errors: ErrorsInterceptorContract,
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel<RootState>() {

    override val _state = MutableStateFlow<RootState>(
        RootState.Navigation(
            R.id.mainFragment,
            R.id.saveFragment,
            R.id.searchFragment
        )
    )
    override val state = _state.asStateFlow()

    fun setupBottomNavigationClick() {
        emit(RootState.Navigation(R.id.mainFragment, R.id.saveFragment, R.id.searchFragment))
    }

    fun interceptorErrors() = launchCoroutine {
        errors.errorsInterceptor().collect() {
            if (it.isNotEmpty()) {
                emit(RootState.InterceptorErrors(it))
            }
        }
    }

    fun checkRegistration() {
        if (firebaseAuth.currentUser != null) {
            emit(RootState.NavigationToMain(R.id.mainFragment))
        }
    }
}
