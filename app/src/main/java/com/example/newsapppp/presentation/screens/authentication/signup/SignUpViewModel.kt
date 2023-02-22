package com.example.newsapppp.presentation.screens.authentication.signup

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.authentication.SignUpUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.FullNameUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidatePasswordUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateRepeatedPasswordUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    fun checkValidationFields(
        name: String,
        email: String,
        password: String,
        repeatedPassword: String
    ) {
        emit(
            SignUpState.CheckState(
                fullName(name),
                validateEmail(email),
                validatePassword(password),
                validateRepeatedPassword.validateRepeatedPassword(password, repeatedPassword)
            )
        )
    }

    fun onSignUnButtonClicked(
        name: String,
        email: String,
        password: String,
    ) = launchCoroutine {
        when {
            email.isEmpty() -> message(R.string.empty_email)
            password.isEmpty() -> message(R.string.empty_password)
            else -> {
                signUpUseCase.signUp(name, email, password)
                    .addOnSuccessListener {
                        message(R.string.successfully_register)
                        emitShared(SignUpAction.Navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()))
                    }
                    .addOnFailureListener {
                        when (it) {
                            is FirebaseAuthWeakPasswordException -> message(R.string.password_lengs)
                            is FirebaseAuthInvalidCredentialsException -> message(R.string.invalid_email)
                            is FirebaseAuthUserCollisionException -> message(R.string.email_registered)
                            else -> message(R.string.invalid_authentication)
                        }
                    }
            }
        }
    }

    fun onSignInBottomClicked() {
        emitShared(SignUpAction.Navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()))
    }

    private fun message(message: Int): Job {
        return emitShared(SignUpAction.ShowMessage(message))
    }

    fun notEnabledYet(){
        emitShared(SignUpAction.ShowMessage(R.string.not_enabled_yet))
    }
}
