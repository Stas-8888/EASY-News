package com.example.newsapppp.presentation.screens.authentication.signup

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.authentication.SignUpUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.FullNameUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidatePasswordUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateRepeatedPasswordUseCase
import com.example.newsapppp.domain.model.UserModel
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
    private val validateEmail: ValidateEmailUseCase,
    private val fullName: FullNameUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateRepeatedPassword: ValidateRepeatedPasswordUseCase,
) : BaseViewModel<SignUpState<String>, SignUpAction>() {

    override val _state = MutableStateFlow<SignUpState<String>>(SignUpState.Loading)

    // Check the validation of the fields entered by the user
    fun checkValidationFields(user: UserModel) {
        val data = SignUpState.CheckState(
            fullName(user.user),
            validateEmail(user.email),
            validatePassword(user.password),
            validateRepeatedPassword.invoke(user.password, user.repeatedPassword)
        )
        emit(data)
    }

    // Perform sign-up operation
    fun onSignUnButtonClicked(user: UserModel) = viewModelScope.launch {
        when {
            user.email.isEmpty() -> message(R.string.empty_email)
            user.password.isEmpty() -> message(R.string.empty_password)
            else -> try {
                signUpUseCase(user)
                    .addOnSuccessListener {
                        message(R.string.successfully_register)
                        navigateToSignInScreen()
                    }
                    .addOnFailureListener {
                        handleSignUpException(it)
                    }
            } catch (e: Exception) {
                message(R.string.invalid_authentication)
            }
        }
    }

    private fun handleSignUpException(exception: Exception) {
        val errorMessageResId = when (exception) {
            is FirebaseAuthWeakPasswordException -> R.string.password_lengs
            is FirebaseAuthInvalidCredentialsException -> R.string.invalid_email
            is FirebaseAuthUserCollisionException -> R.string.email_registered
            else -> R.string.invalid_authentication
        }
        message(errorMessageResId)
    }

    // Handle the sign-in button click event
    fun navigateToSignInScreen() {
        val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
        emitAction(SignUpAction.Navigate(action))
    }

    private fun message(message: Int) = emitAction(SignUpAction.ShowMessage(message))
    fun onNotEnabledBottomClicked() = emitAction(SignUpAction.ShowMessage(R.string.not_enabled_yet))
}
