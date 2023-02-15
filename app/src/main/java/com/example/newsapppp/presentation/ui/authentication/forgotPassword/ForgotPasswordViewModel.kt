package com.example.newsapppp.presentation.ui.authentication.forgotPassword

import com.example.newsapppp.R
import com.example.newsapppp.core.ProvideResourcesContract
import com.example.newsapppp.domain.interactors.authentication.ForgotPasswordUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPassword: ForgotPasswordUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val provideResources: ProvideResourcesContract
) : BaseViewModel<ForgotPasswordState<String>, ForgotPasswordAction>() {

    override val _state = MutableStateFlow<ForgotPasswordState<String>>(ForgotPasswordState.Loading)
    override val state = _state.asStateFlow()

    override val _shared = MutableSharedFlow<ForgotPasswordAction>()
    override val shared = _shared.asSharedFlow()

    fun onForgotPasswordClicked(email: String) = launchCoroutine {
        when {
            email.isEmpty() -> emit(ForgotPasswordState.Failure(provideResources.string(R.string.empty_email)))
            else -> {
                forgotPassword.forgotPassword(email)
                    .addOnSuccessListener {
                        emit(ForgotPasswordState.Failure(provideResources.string(R.string.email_sent)))
                    }.addOnFailureListener {
                        emit(ForgotPasswordState.Failure(it.message))
                    }
            }
        }
    }

    fun isEmailChanged(email: String) {
        emit(ForgotPasswordState.CheckEmail(validateEmail(email)))
    }
}
