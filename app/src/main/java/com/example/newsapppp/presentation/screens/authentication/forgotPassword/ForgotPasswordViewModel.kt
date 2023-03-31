package com.example.newsapppp.presentation.screens.authentication.forgotPassword

import androidx.lifecycle.viewModelScope
import com.example.newsapppp.R
import com.example.newsapppp.domain.interactors.authentication.ForgotPasswordUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.presentation.extensions.isOffline
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPassword: ForgotPasswordUseCase,
    private val validateEmail: ValidateEmailUseCase,
) : BaseViewModel<ForgotPasswordState<String>, ForgotPasswordAction>() {

    override val _state = MutableStateFlow<ForgotPasswordState<String>>(ForgotPasswordState.Loading)

    /**
     * Launches when the user clicks on the "forgot password" button.
     * This function checks if the device is offline or not.
     * This function initiates the process of resetting the user's password.
     * @param user The user model containing the email address of the user.
     */
    fun onForgotPasswordClicked(user: UserModel) = viewModelScope.launch {
        when {
            isOffline() -> emitAction(ForgotPasswordAction.ShowNetworkDialog(R.string.internet_disconnected))
            user.email.isEmpty() -> emitAction(ForgotPasswordAction.ShowMessage(R.string.empty_email))
            else -> forgotPasswordUser(user)
        }
    }

    /**
     * Sends a "forgot password" email to the specified user.
     * @param user The user model containing the email address of the user.
     */
    private suspend fun forgotPasswordUser(user: UserModel) = forgotPassword(user)
        .addOnSuccessListener {
            emitAction(ForgotPasswordAction.ShowMessage(R.string.email_sent))
        }.addOnFailureListener {
            emitAction(ForgotPasswordAction.ShowMessage(R.string.wrong_email))
        }


    /**
     * This function is called when the email input is changed to validate the email format.
     */
    fun isEmailChanged(email: String) = emit(ForgotPasswordState.CheckEmail(validateEmail(email)))
}
