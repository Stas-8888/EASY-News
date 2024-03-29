package com.example.newsapppp.presentation.screens.authentication.signup

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.example.newsapppp.domain.use_case.authentication.SignUpUseCase
import com.example.newsapppp.domain.use_case.authentication.validation.UserNameUseCase
import com.example.newsapppp.domain.use_case.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.use_case.authentication.validation.ValidatePasswordUseCase
import com.example.newsapppp.domain.use_case.authentication.validation.ValidateRepeatedPasswordUseCase
import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.presentation.extensions.isOffline
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUp: SignUpUseCase,
    private val userName: UserNameUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateRepeatedPassword: ValidateRepeatedPasswordUseCase,
) : BaseViewModel<SignUpState<String>, SignUpAction>() {

    override val _state = MutableStateFlow<SignUpState<String>>(SignUpState.Loading)

    /**
     * Check the validation of the fields entered by the user.
     */
    fun checkValidationFields(user: UserModel) {
        val data = SignUpState.CheckState(
            userName(user.user),
            validateEmail(user.email),
            validatePassword(user.password),
            validateRepeatedPassword.invoke(user.password, user.repeatedPassword)
        )
        emit(data)
    }

    /**
     * Perform sign-up operation.
     */
    fun onSignUnButtonClicked(user: UserModel) = when {
        isOffline() -> emitAction(SignUpAction.ShowNetworkDialog(R.string.internet_disconnected))
        user.email.isEmpty() -> emitAction(SignUpAction.ShowMessage(R.string.empty_email))
        user.password.isEmpty() -> emitAction(SignUpAction.ShowMessage(R.string.empty_password))
        else -> signUpUser(user)
    }

    /**
     * Registers a new user by calling [signUp] function and handles the success and failure cases.
     * On successful registration, it navigates to sign-in screen and emits a success message.
     * On failure, it emits an appropriate error message depending on the type of exception.
     * @param user the user model to be registered
     */
    private fun signUpUser(user: UserModel) = viewModelScope.launch {
        signUp(user)
            .addOnSuccessListener {
                navigateToSignInScreen()
                emitAction(SignUpAction.ShowMessage(R.string.successfully_register))
            }
            .addOnFailureListener {
                val errorMessageResId = when (it) {
                    is FirebaseAuthWeakPasswordException -> R.string.password_lengs
                    is FirebaseAuthInvalidCredentialsException -> R.string.invalid_email
                    is FirebaseAuthUserCollisionException -> R.string.email_registered
                    else -> R.string.invalid_authentication
                }
                emitAction(SignUpAction.ShowMessage(errorMessageResId))
            }
    }

    /**
     * Handle the sign-in button click event.
     */
    fun navigateToSignInScreen() {
        val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
        emitAction(SignUpAction.Navigate(action))
    }

    fun onNotEnabledBottomClicked() = emitAction(SignUpAction.ShowMessage(R.string.not_enabled_yet))
}
