package com.example.newsapppp.presentation.screens.authentication.forgotPassword

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.authentication.ForgotPasswordUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPassword: ForgotPasswordUseCase,
    private val validateEmail: ValidateEmailUseCase
) : BaseViewModel<ForgotPasswordState<String>, ForgotPasswordAction>() {

    override val _state = MutableStateFlow<ForgotPasswordState<String>>(ForgotPasswordState.Loading)

    // Called when the user clicks on the Forgot Password button
    fun onForgotPasswordClicked(user: UserModel) = viewModelScope.launch {
        when {
            user.email.isEmpty() -> emitAction(ForgotPasswordAction.ShowMessage(R.string.empty_email))
            else -> try {
                forgotPassword(user)
                emitAction(ForgotPasswordAction.ShowMessage(R.string.email_sent))
            } catch (e: Exception) {
                emitAction(ForgotPasswordAction.ShowMessage(R.string.wrong_email))
            }
        }
    }

    // This function is called when the email input is changed to validate the email format
    fun isEmailChanged(email: String) = emit(ForgotPasswordState.CheckEmail(validateEmail(email)))
}
