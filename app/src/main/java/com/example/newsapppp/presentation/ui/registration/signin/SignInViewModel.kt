package com.example.newsapppp.presentation.ui.registration.signin

import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.domain.interactors.authentication.SignInUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidatePasswordUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signIn: SignInUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
) : BaseViewModel<FirebaseState<String>>() {

    override val _state = MutableStateFlow<FirebaseState<String>>(FirebaseState.Loading)
    override val state = _state.asStateFlow()

    fun onSignInClicked(email: String, password: String) = launchCoroutine {
        signIn.signIn(email, password) {
            launchCoroutine {
                emitState(it)
            }
        }
    }

    fun isValidEmail(email: String) = launchCoroutine {
        emitState(FirebaseState.CheckEmail(validateEmail(email)))
    }

    fun isValidPassword(password: String) = launchCoroutine {
        emitState(FirebaseState.CheckPassword(validatePassword(password)))
    }
}
