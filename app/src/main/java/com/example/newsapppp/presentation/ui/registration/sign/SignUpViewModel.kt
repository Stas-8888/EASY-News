package com.example.newsapppp.presentation.ui.registration.sign

import com.example.newsapppp.presentation.utils.extensions.launchCoroutine
import com.example.newsapppp.domain.interactors.registration.*
import com.example.newsapppp.presentation.ui.base.BaseViewModel
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
) : BaseViewModel<SignUpState>() {

    override val _state = MutableStateFlow<SignUpState>(SignUpState.Success)
    override val state = _state.asStateFlow()

    fun isValidate(
        name: String,
        email: String,
        password: String,
        repeatedPassword: String
    ) = launchCoroutine {
        _state.emit(
            SignUpState.CheckState(
                fullName(name),
                validateEmail(email),
                validatePassword(password),
                validateRepeatedPassword.validateRepeatedPassword(password, repeatedPassword)
            )
        )
    }

    fun signUpUser(
        name: String,
        email: String,
        password: String,
        repeatedPassword: String,
        navigateTo: Unit
    ) = launchCoroutine {
        val result = fullName(name) == "successful"
                && validateEmail(email) == "successful"
                && validatePassword(password) == "successful"
                && validateRepeatedPassword.validateRepeatedPassword(
            password,
            repeatedPassword
        ) == "successful"

        if (result) {
            signUpUseCase.signUp(name, email, password, navigateTo)
        } else {
            _state.emit(SignUpState.Error)
        }
    }
}
