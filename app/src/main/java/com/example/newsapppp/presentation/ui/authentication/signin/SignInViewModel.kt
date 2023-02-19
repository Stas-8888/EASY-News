package com.example.newsapppp.presentation.ui.authentication.signin

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.authentication.SignInUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidatePasswordUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signIn: SignInUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase
) : BaseViewModel<SignInState<String>, SignInAction>() {

    override val _state = MutableStateFlow<SignInState<String>>(SignInState.Loading(false))
    override val state = _state.asStateFlow()

    override val _shared = MutableSharedFlow<SignInAction>()
    override val shared = _shared.asSharedFlow()

    fun onSignInButtonClicked(email: String, password: String) = launchCoroutine {
        when {
            email.isEmpty() -> emitShared(SignInAction.Message(R.string.empty_email))
            password.isEmpty() -> emitShared(SignInAction.Message(R.string.empty_password))
            else -> {
                signIn.signIn(email, password)
                    .addOnSuccessListener {
                        emit(SignInState.Loading(true))
                        emitShared(SignInAction.Message(R.string.successfully_sign_in))
                        emitShared(SignInAction.Navigate(SignInFragmentDirections.actionSignInFragmentToMainFragment()))
                    }.addOnFailureListener {
                        emitShared(SignInAction.Message(R.string.authentication_failed))
                    }
            }
        }
    }

    fun onSkipButtonClicked() =
        emitShared(SignInAction.Navigate(SignInFragmentDirections.actionSignInFragmentToMainFragment()))

    fun onSignUpButtonClicked() =
        emitShared(SignInAction.Navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment()))

    fun onForgotPasswordButtonClicked() =
        emitShared(SignInAction.Navigate(SignInFragmentDirections.actionSignInFragmentToForgotPasswordFragment()))

    fun isEmailChanged(email: String) = emit(SignInState.CheckEmail(validateEmail(email)))

    fun isPasswordChanged(password: String) =
        emit(SignInState.CheckPassword(validatePassword(password)))
}
