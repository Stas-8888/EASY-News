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

    // Called when the user clicks on the Sign In button
    fun onSignInButtonClicked(user: UserModel) = viewModeLaunch {
        when {
            user.email.isEmpty() -> emitShared(SignInAction.ShowMessage(R.string.empty_email))
            user.password.isEmpty() -> emitShared(SignInAction.ShowMessage(R.string.empty_password))
            else -> try {
                signIn(user)
                emit(SignInState.Loading(true))
                emitShared(SignInAction.ShowMessage(R.string.successfully_sign_in))
                emitShared(SignInAction.Navigate(SignInFragmentDirections.actionSignInFragmentToMainFragment()))
            } catch (e: Exception) {
                emitShared(SignInAction.ShowMessage(R.string.authentication_failed))
            }
        }
    }

    // Called when the user clicks on the Skip button
    fun onSkipButtonClicked() =
        emitShared(SignInAction.Navigate(SignInFragmentDirections.actionSignInFragmentToMainFragment()))

    // Called when the user clicks on the Sign Up button
    fun onSignUpButtonClicked() =
        emitShared(SignInAction.Navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment()))

    // Called when the user clicks on the Forgot Password button
    fun onForgotPasswordButtonClicked() =
        emitShared(SignInAction.Navigate(SignInFragmentDirections.actionSignInFragmentToForgotPasswordFragment()))

    // Called when the email EditText text changes
    fun isEmailChanged(email: String) = emit(SignInState.CheckEmail(validateEmail(email)))

    // Called when the password EditText text changes
    fun isPasswordChanged(password: String) =
        emit(SignInState.CheckPassword(validatePassword(password)))
}
