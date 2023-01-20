package com.example.newsapppp.presentation.ui.registration.signup

import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.domain.interactors.firebase.*
import com.example.newsapppp.domain.interactors.firebase.validation.FullNameUseCase
import com.example.newsapppp.domain.interactors.firebase.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.firebase.validation.ValidatePasswordUseCase
import com.example.newsapppp.domain.interactors.firebase.validation.ValidateRepeatedPasswordUseCase
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import com.example.newsapppp.presentation.utils.extensions.launchCoroutine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validateEmail: ValidateEmailUseCase,
    private val fullName: FullNameUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateRepeatedPassword: ValidateRepeatedPasswordUseCase
) : BaseViewModel<FirebaseState<String>>() {

    override val _state = MutableStateFlow<FirebaseState<String>>(FirebaseState.Loading)
    override val state = _state.asStateFlow()

    fun checkValidationFields(
        name: String,
        email: String,
        password: String,
        repeatedPassword: String
    ) = launchCoroutine {
        _state.emit(
            FirebaseState.CheckState(
                fullName(name),
                validateEmail(email),
                validatePassword(password),
                validateRepeatedPassword.validateRepeatedPassword(password, repeatedPassword)
            )
        )
    }

    fun onSignUpClick(
        name: String,
        email: String,
        password: String,
    ) = launchCoroutine {
        signUpUseCase.signUp(name, email, password) {
            launchCoroutine {
                emitState(it)
            }
        }
    }
}
