package com.example.newsapppp.presentation.ui.registration.login

import com.example.newsapppp.R
import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.domain.interactors.firebase.LoginUseCase
import com.example.newsapppp.domain.interactors.firebase.ValidatePasswordUseCase
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import com.example.newsapppp.presentation.utils.extensions.launchCoroutine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUseCase,
    private val validatePassword: ValidatePasswordUseCase,
) : BaseViewModel<FirebaseState<String>>(){

    override val _state = MutableStateFlow<FirebaseState<String>>(FirebaseState.Loading)
    override val state = _state.asStateFlow()

    fun signInClicked(email: String, password: String) = launchCoroutine {
        emitState(FirebaseState.Loading)
        emitState(FirebaseState.Navigate(R.id.mainFragment))
        login.signIn(email, password) {
            launchCoroutine{
                emitState(it)
            }
        }

    }
//
//    fun isValidEmail(email: String) = launchCoroutine {
//        emitState(LoginState.CheckEmail(Validator.isValidEmail(email).toString()))
//    }
//
//    fun isValidPassword(password: String) = launchCoroutine {
//        emitState(LoginState.CheckPassword(validatePassword(password)))
//    }
}


//if (Validator.isValidEmail(email) == R.string.successful && isEmpty(email) == R.string.successful &&
//validatePassword(password) == "successful"
//) {


//    fun isValidate(
//        email: String,
//        password: String,
//        navigateTo: () -> Unit
//    ) = launchCoroutine {
//        val result =
//            validateEmail(email) == "successful" && validatePassword(password) == "successful"
//
//        if (result) {
//            signInClicked(email, password, navigateTo)
//        } else {
//            R.string.error
//        }
//    }

//    fun signInClicked(email: String, password: String, navigateTo: () -> Unit) = launchCoroutine {
//        if (isValidateEmail(email) == R.string.successful &&
//            isEmpty(email) == R.string.successful &&
//            validatePassword(password) == "successful"
//        ) {
//            login.signIn(email, password).addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    launchCoroutine {
//                        emitState(
//                            LoginState.Success(
//                                navigateTo(),
//                                R.string.successes_registered
//                            )
//                        )
//                    }
//                }
//            }.addOnFailureListener {
//                launchCoroutine {
//                    emitState(LoginState.Error(R.string.authentication_failed))
//                }
//            }
//        } else {
//            emitState(LoginState.Error(R.string.wrong_email_password))
//        }
//    }

