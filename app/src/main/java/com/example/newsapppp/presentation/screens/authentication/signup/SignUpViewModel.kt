package com.example.newsapppp.presentation.screens.authentication.signup

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.authentication.SignUpUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.FullNameUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidatePasswordUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateRepeatedPasswordUseCase
import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.presentation.extensions.viewModeLaunch
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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
    override val _shared = MutableSharedFlow<SignUpAction>()

    // Check the validation of the fields entered by the user
    fun checkValidationFields(user: UserModel) {
        emit(
            SignUpState.CheckState(
                fullName(user.user),
                validateEmail(user.email),
                validatePassword(user.password),
                validateRepeatedPassword.invoke(user.password, user.repeatedPassword)
            )
        )
    }

    // Perform sign-up operation
    fun onSignUnButtonClicked(user: UserModel) = viewModeLaunch {
        when {
            user.email.isEmpty() -> message(R.string.empty_email)
            user.password.isEmpty() -> message(R.string.empty_password)
            else -> try {
                signUpUseCase(user)
                message(R.string.successfully_register)
                emitShared(SignUpAction.Navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()))
            } catch (e: Exception) {
                when (e) {
                    is FirebaseAuthWeakPasswordException -> message(R.string.password_lengs)
                    is FirebaseAuthInvalidCredentialsException -> message(R.string.invalid_email)
                    is FirebaseAuthUserCollisionException -> message(R.string.email_registered)
                    else -> message(R.string.invalid_authentication)
                }
            }
        }
    }

    // Handle the sign-in button click event
    fun onSignInBottomClicked() {
        val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
        emitShared(SignUpAction.Navigate(action))
    }

    private fun message(message: Int) = emitShared(SignUpAction.ShowMessage(message))
    fun onNotEnabledBottomClicked() = emitShared(SignUpAction.ShowMessage(R.string.not_enabled_yet))
}
