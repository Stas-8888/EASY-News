package com.example.newsapppp.presentation.ui.registration.login

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.registration.LoginUseCase
import com.example.newsapppp.domain.interactors.registration.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.registration.ValidatePasswordUseCase
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import com.example.newsapppp.presentation.utils.extensions.launchCoroutine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
) : BaseViewModel<LoginState>() {

    override val _state = MutableStateFlow<LoginState>(LoginState.Loading)
    override val state = _state.asStateFlow()

    fun signInClicked(email: String, password: String, navigateTo: () -> Unit) = launchCoroutine {
        if (validateEmail(email) == "successful" && validatePassword(password) == "successful") {
            login.signIn(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    launchCoroutine {
                        emitState(
                            LoginState.Success(
                                navigateTo(),
                                R.string.successes_registered
                            )
                        )
                    }
                }
            }.addOnFailureListener {
                launchCoroutine {
                    emitState(LoginState.Error(R.string.authentication_failed))
                }
            }
        } else {
                emitState(LoginState.Error(R.string.wrong_email_password))
        }
//        login.signIn(email, password, navigateTo) {
//            launchCoroutine {
//                emitState(it)
//            }
//        }
    }

    fun isValidEmail(data: String) = launchCoroutine {
        emitState(LoginState.CheckEmail(validateEmail(data)))
    }

    fun isValidPassword(data: String) = launchCoroutine {
        emitState(LoginState.CheckPassword(validatePassword(data)))
    }

    fun isValidate(
        email: String,
        password: String,
        navigateTo: () -> Unit
    ) = launchCoroutine {
        val result =
            validateEmail(email) == "successful" && validatePassword(password) == "successful"

        if (result) {
            signInClicked(email, password, navigateTo)
        } else {
            R.string.error
        }
    }
}
