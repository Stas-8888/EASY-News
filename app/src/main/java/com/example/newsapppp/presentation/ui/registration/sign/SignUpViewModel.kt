package com.example.newsapppp.presentation.ui.registration.sign

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.interactors.registration.*
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    fun signUpUser(name: String, email: String, password: String, navigateTo: Unit) =
        viewModelScope.launch {
            signUpUseCase.signUp(name, email, password, navigateTo)
        }

    fun checkEmail(data: String): String {
        return validateEmail(data)
    }

    fun checkPassword(data: String): String {
        return validatePassword(data)
    }

    fun checkFullName(data: String): String {
        return fullName(data)
    }

    fun checkRepeatedPassword(password: String, repeatedPassword: String): String {
        return validateRepeatedPassword.validateRepeatedPassword(password, repeatedPassword)
    }
}
