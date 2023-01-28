package com.example.newsapppp.presentation.ui.authentication.signin

import com.example.newsapppp.R
import com.example.newsapppp.core.ProvideResourcesContract
import com.example.newsapppp.domain.interactors.authentication.SignInUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidatePasswordUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.ui.authentication.AuthState
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signIn: SignInUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val provideResources: ProvideResourcesContract
) : BaseViewModel<AuthState<String>>() {

    override val _state = MutableStateFlow<AuthState<String>>(AuthState.Loading)
    override val state = _state.asStateFlow()

    fun signInButtonClicked(email: String, password: String) = launchCoroutine {
        when {
            email.isEmpty() -> emitState(AuthState.Failure(provideResources.string(R.string.empty_email)))
            password.isEmpty() -> emitState(AuthState.Failure(provideResources.string(R.string.empty_password)))
            else -> signIn.signIn(email, password) {
                emitState(it)
            }
        }
    }

    fun isEmailChanged(email: String) {
        emitState(AuthState.CheckEmail(validateEmail(email)))
    }

    fun isPasswordChanged(password: String) {
        emitState(AuthState.CheckPassword(validatePassword(password)))
    }
}
