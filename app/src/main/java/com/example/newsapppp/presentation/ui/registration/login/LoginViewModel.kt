package com.example.newsapppp.presentation.ui.registration.login

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.domain.interactors.registration.LoginUseCase
import com.example.newsapppp.domain.interactors.registration.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.registration.ValidatePasswordUseCase
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import com.example.newsapppp.core.extensions.launchCoroutine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase
) : BaseViewModel<LoginState>() {

    override val _state = MutableStateFlow<LoginState>(LoginState.Error)
    override val state = _state.asStateFlow()

    private fun loginUser(email: String, password: String, navigateTo: () -> Unit) =
        viewModelScope.launch {
            login.signIn(email, password, navigateTo)
        }

    fun isValidEmail(data: String) = launchCoroutine{
        _state.emit(LoginState.CheckState(validateEmail(data)))
    }

    fun isValidPassword(data: String) = launchCoroutine{
        _state.emit(LoginState.CheckState(validatePassword(data)))
    }

    fun isValidate(
        email: String,
        password: String,
        navigateTo: () -> Unit
    ) = viewModelScope.launch {
        val result =
            validateEmail(email) == "successful" && validatePassword(password) == "successful"

        if (result) {
            loginUser(email, password, navigateTo)
        } else {
            _state.emit(LoginState.Error)
        }
    }
}
