package com.example.newsapppp.presentation.ui.authentication.signin

import com.example.newsapppp.R
import com.example.newsapppp.core.ProvideResourcesContract
import com.example.newsapppp.domain.interactors.authentication.SignInUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidateEmailUseCase
import com.example.newsapppp.domain.interactors.authentication.validation.ValidatePasswordUseCase
import com.example.newsapppp.presentation.extensions.launchCoroutine
import com.example.newsapppp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signIn: SignInUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val provideResources: ProvideResourcesContract
) : BaseViewModel<SignInState<String>, SignInAction>() {

    override val _state = MutableStateFlow<SignInState<String>>(SignInState.Loading)
    override val state = _state.asStateFlow()

    override val _shared = MutableSharedFlow<SignInAction>()
    override val shared = _shared.asSharedFlow()

    fun signInButtonClicked(email: String, password: String) = launchCoroutine {
        when {
            email.isEmpty() -> emit(SignInState.Failure(provideResources.string(R.string.empty_email)))
            password.isEmpty() -> emit(SignInState.Failure(provideResources.string(R.string.empty_password)))
            else -> {
                signIn.signIn(email, password)
                    .addOnSuccessListener {
                        emit(SignInState.Success(provideResources.string(R.string.successfully_sign_in)))
                        emitShared(SignInAction.Navigate(R.id.mainFragment))
                    }.addOnFailureListener {
                        emit(SignInState.Failure(provideResources.string(R.string.authentication_failed)))
                    }
            }
        }
    }

    fun onSkipClicked() =
        emit(SignInState.NavigateSkip(SignInFragmentDirections.actionSignInFragmentToMainFragment()))

    fun onSignUpClicked() =
        emit(SignInState.NavigateSkip(SignInFragmentDirections.actionSignInFragmentToSignUpFragment()))

    fun isEmailChanged(email: String) = emit(SignInState.CheckEmail(validateEmail(email)))
    fun onForgotPasswordClicked() =
        emit(SignInState.NavigateForgotPassword(R.id.forgotPasswordFragment))

    fun isPasswordChanged(password: String) =
        emit(SignInState.CheckPassword(validatePassword(password)))
}
