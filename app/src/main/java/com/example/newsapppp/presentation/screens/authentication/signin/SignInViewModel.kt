package com.example.newsapppp.presentation.screens.authentication.signin

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.authentication.SignInUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidatePasswordUseCase
import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.presentation.extensions.isOffline
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signIn: SignInUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase
) : BaseViewModel<SignInState<String>, SignInAction>() {

    override val _state = MutableStateFlow<SignInState<String>>(SignInState.Loading(false))

    /**
     * Called when the user clicks on the Sign In button.
     */
    fun onSignInButtonClicked(user: UserModel) = viewModelScope.launch {
        when {
            isOffline() -> emitAction(SignInAction.ShowNetworkDialog(R.string.internet_disconnected))
            validateEmail(user.email).none() -> emitAction(SignInAction.ShowMessage(R.string.empty_email))
            validatePassword(user.password).none() -> emitAction(SignInAction.ShowMessage(R.string.empty_password))
            else -> signInUser(user)
        }
    }

    private suspend fun signInUser(user: UserModel) = try {
        signIn(user)
            .addOnSuccessListener {
                emit(SignInState.Loading(true))
                emitAction(SignInAction.ShowMessage(R.string.successfully_sign_in))
                emitAction(SignInAction.Navigate(SignInFragmentDirections.actionSignInFragmentToMainFragment()))
            }.addOnFailureListener {
                emitAction(SignInAction.ShowMessage(R.string.authentication_failed))
            }
    } catch (e: Exception) {
        emitAction(SignInAction.ShowMessage(R.string.authentication_failed))
    }

    /**
     * Called when the user clicks on the Skip button.
     */
    fun onSkipButtonClicked() =
        emitAction(SignInAction.Navigate(SignInFragmentDirections.actionSignInFragmentToMainFragment()))

    /**
     * Called when the user clicks on the Sign Up button.
     */
    fun onSignUpButtonClicked() =
        emitAction(SignInAction.Navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment()))

    /**
     * Called when the user clicks on the Forgot Password button.
     */
    fun onForgotPasswordButtonClicked() =
        emitAction(SignInAction.Navigate(SignInFragmentDirections.actionSignInFragmentToForgotPasswordFragment()))

    /**
     * Called when the email EditText text changes.
     */
    fun isEmailChanged(email: String) = emit(SignInState.CheckEmail(validateEmail(email)))

    /**
     * Called when the password EditText text changes.
     */
    fun isPasswordChanged(password: String) =
        emit(SignInState.CheckPassword(validatePassword(password)))
}
