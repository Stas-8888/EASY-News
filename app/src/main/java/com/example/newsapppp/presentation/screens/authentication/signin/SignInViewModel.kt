package com.example.newsapppp.presentation.screens.authentication.signin

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.authentication.SignInUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidatePasswordUseCase
import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.presentation.extensions.viewModeLaunch
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signIn: SignInUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase
) : BaseViewModel<SignInState<String>, SignInAction>() {

    override val _state = MutableStateFlow<SignInState<String>>(SignInState.Loading(false))
    override val _shared = MutableSharedFlow<SignInAction>()

    fun onSignInButtonClicked(user: UserModel) = viewModeLaunch {
        when {
            user.email.isEmpty() -> emitShared(SignInAction.ShowMessage(R.string.empty_email))
            user.password.isEmpty() -> emitShared(SignInAction.ShowMessage(R.string.empty_password))
            else -> {
                signIn.signIn(user)
                    .addOnSuccessListener {
                        emit(SignInState.Loading(true))
                        emitShared(SignInAction.ShowMessage(R.string.successfully_sign_in))
                        emitShared(SignInAction.Navigate(SignInFragmentDirections.actionSignInFragmentToMainFragment()))
                    }.addOnFailureListener {
                        emitShared(SignInAction.ShowMessage(R.string.authentication_failed))
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

    fun isPasswordChanged(password: String) = emit(SignInState.CheckPassword(validatePassword(password)))
}
