package com.example.newsapppp.presentation.ui.authentication.signin

import com.example.newsapppp.R
import com.example.newsapppp.core.ProvideResourcesContract
import com.example.newsapppp.domain.interactors.authentication.SignInUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidatePasswordUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
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
) : BaseViewModel<SignInState<String>>() {

    override val _state = MutableStateFlow<SignInState<String>>(SignInState.Loading)
    override val state = _state.asStateFlow()

    fun signInButtonClicked(email: String, password: String) = launchCoroutine {
        when {
            email.isEmpty() -> emitState(SignInState.Failure(provideResources.string(R.string.empty_email)))
            password.isEmpty() -> emitState(SignInState.Failure(provideResources.string(R.string.empty_password)))
            else -> signIn.signIn(email, password) {
                emitState(it)
                emitState(SignInState.Navigate(R.id.mainFragment))
            }
        }
    }

    fun onForgotPasswordClicked() {
        emitState(SignInState.NavigateForgotPassword(R.id.forgotPasswordFragment))
    }

    fun onSkipClicked() {
        emitState(SignInState.NavigateSkip(R.id.mainFragment))
    }

    fun onSkipSignUpClicked() {
        emitState(SignInState.NavigateSkip(R.id.signUpFragment))
    }

    fun isEmailChanged(email: String) {
        emitState(SignInState.CheckEmail(validateEmail(email)))
    }

    fun isPasswordChanged(password: String) {
        emitState(SignInState.CheckPassword(validatePassword(password)))
    }
}
