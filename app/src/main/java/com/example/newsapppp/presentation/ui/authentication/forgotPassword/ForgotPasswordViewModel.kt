package com.example.newsapppp.presentation.ui.authentication.forgotPassword

import com.example.newsapppp.presentation.ui.authentication.AuthState
import com.example.newsapppp.domain.interactors.authentication.ForgotPasswordUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPassword: ForgotPasswordUseCase,
    private val validateEmail: ValidateEmailUseCase
) : BaseViewModel<AuthState<String>>() {

    override val _state = MutableStateFlow<AuthState<String>>(AuthState.Loading)
    override val state = _state.asStateFlow()

    fun forgotPassword(email: String) = launchCoroutine {
        forgotPassword.forgotPassword(email) {
            launchCoroutine {
                emitState(it)
            }
        }
    }

    fun isValidEmail(email: String) = launchCoroutine {
        emitState(AuthState.CheckEmail(validateEmail(email)))
    }
}