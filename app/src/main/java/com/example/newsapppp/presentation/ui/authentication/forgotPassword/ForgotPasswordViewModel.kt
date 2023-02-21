package com.example.newsapppp.presentation.ui.authentication.forgotPassword

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.authentication.ForgotPasswordUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPassword: ForgotPasswordUseCase,
    private val validateEmail: ValidateEmailUseCase
) : BaseViewModel<ForgotPasswordState<String>, ForgotPasswordAction>() {

    override val _state = MutableStateFlow<ForgotPasswordState<String>>(ForgotPasswordState.Loading)
    override val _shared = MutableSharedFlow<ForgotPasswordAction>()

    fun onForgotPasswordClicked(email: String) = launchCoroutine {
        when {
            email.isEmpty() -> emitShared(ForgotPasswordAction.Message(R.string.empty_email))
            else -> {
                forgotPassword.forgotPassword(email)
                    .addOnSuccessListener {
                        emitShared(ForgotPasswordAction.Message(R.string.email_sent))
                    }.addOnFailureListener {
                        emitShared(ForgotPasswordAction.Message(R.string.wrong_email))
                    }
            }
        }
    }

    fun isEmailChanged(email: String) {
        emit(ForgotPasswordState.CheckEmail(validateEmail(email)))
    }
}
