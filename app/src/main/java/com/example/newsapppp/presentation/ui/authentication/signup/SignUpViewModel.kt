package com.example.newsapppp.presentation.ui.authentication.signup

import com.example.newsapppp.R
import com.example.newsapppp.core.ProvideResourcesContract
import com.example.newsapppp.domain.interactors.authentication.SignUpUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.FullNameUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidatePasswordUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateRepeatedPasswordUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateEmail: ValidateEmailUseCase,
    private val fullName: FullNameUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateRepeatedPassword: ValidateRepeatedPasswordUseCase,
    private val provideResources: ProvideResourcesContract
) : BaseViewModel<SignUpState<String>>() {

    override val _state = MutableStateFlow<SignUpState<String>>(SignUpState.Loading)
    override val state = _state.asStateFlow()

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

    fun onBackPressClick() {
        emit(SignUpState.Navigate(R.id.loginFragment))
    }

    fun signUnButtonClicked(
        name: String,
        email: String,
        password: String,
    ) = launchCoroutine {
        when {
            email.isEmpty() -> failure(R.string.empty_email)
            password.isEmpty() -> failure(R.string.empty_password)
            else -> {
                signUpUseCase.signUp(name, email, password)
                    .addOnSuccessListener {
                        emit(SignUpState.Success(provideResources.string(R.string.successfully_register)))
                        emit(SignUpState.Navigate(R.id.loginFragment))
                    }
                    .addOnFailureListener {
                        when (it) {
                            is FirebaseAuthWeakPasswordException -> failure(R.string.password_lengs_6)
                            is FirebaseAuthInvalidCredentialsException -> failure(R.string.invalid_email)
                            is FirebaseAuthUserCollisionException -> failure(R.string.email_registered)
                            else -> failure(R.string.invalid_authentication)
                        }
                    }
            }
        }
    }

    private fun failure(message: Int): Job {
        return emit(SignUpState.Failure(provideResources.string(message)))
    }
}
