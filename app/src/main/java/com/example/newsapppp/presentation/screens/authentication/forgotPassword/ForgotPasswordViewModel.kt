package com.example.newsapppp.presentation.screens.authentication.forgotPassword

import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.authentication.ForgotPasswordUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.presentation.extensions.viewModeLaunch
import com.example.newsapppp.presentation.screens.base.BaseViewModel
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

    fun onForgotPasswordClicked(user: UserModel) = viewModeLaunch {
        when {
            user.email.isEmpty() -> emitShared(ForgotPasswordAction.ShowMessage(R.string.empty_email))
            else -> try {
                forgotPassword(user)
                emitShared(ForgotPasswordAction.ShowMessage(R.string.email_sent))
            } catch (e: Exception) {
                emitShared(ForgotPasswordAction.ShowMessage(R.string.wrong_email))
            }
        }
    }

    fun isEmailChanged(email: String) = emit(ForgotPasswordState.CheckEmail(validateEmail(email)))
}
