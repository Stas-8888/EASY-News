package com.example.newsapppp.presentation.screens.authentication.signin

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.example.newsapppp.domain.use_case.authentication.SignInUseCase
import com.example.newsapppp.domain.use_case.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.use_case.authentication.validation.ValidatePasswordUseCase
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
    fun onSignInButtonClicked(user: UserModel) = when {
        isOffline() -> emitAction(SignInAction.ShowNetworkDialog(R.string.internet_disconnected))
        user.email.isEmpty() -> emitAction(SignInAction.ShowMessage(R.string.empty_email))
        user.password.isEmpty() -> emitAction(SignInAction.ShowMessage(R.string.empty_password))
        else -> signInUser(user)
    }

    /**
     * Signs in the user using the provided [user] model and updates the UI accordingly.
     * @param user the [UserModel] to sign in
     */
    private fun signInUser(user: UserModel) = viewModelScope.launch {
        signIn(user)
            .addOnSuccessListener {
                emit(SignInState.Loading(true))
                emitAction(SignInAction.ShowMessage(R.string.successfully_sign_in))
                emitAction(SignInAction.Navigate(SignInFragmentDirections.actionSignInFragmentToMainFragment()))
            }.addOnFailureListener {
                emitAction(SignInAction.ShowMessage(R.string.authentication_failed))
            }
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
