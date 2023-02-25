package com.example.newsapppp.presentation.screens.authentication.signin

import androidx.fragment.app.viewModels
import com.example.newsapppp.databinding.FragmentSignInBinding
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment :
    BaseFragment<SignInState<String>, SignInAction, FragmentSignInBinding, SignInViewModel>(
        FragmentSignInBinding::inflate
    ) {
    override val viewModel by viewModels<SignInViewModel>()

    override fun onClickListener() = with(binding) {
        edLogin.afterTextChanged {
            viewModel.isEmailChanged(it)
        }
        edLoginPassword.afterTextChanged {
            viewModel.isPasswordChanged(it)
        }
        btSignIn.setOnClickListener {
            it.shake()
            it.hideKeyboard()
            viewModel.onSignInButtonClicked(emailText(), passwordText())
        }
        btForgotPassword.setOnClickListener {
            it.hideKeyboard()
            viewModel.onForgotPasswordButtonClicked()
        }
        btSignUp.setOnClickListener {
            it.clickAnim()
            it.hideKeyboard()
            viewModel.onSignUpButtonClicked()
        }
        btSkip.setOnClickListener {
            it.clickAnim()
            it.hideKeyboard()
            viewModel.onSkipButtonClicked()
        }
    }

    private fun emailText(): String = binding.edLogin.text.toString()
    private fun passwordText(): String = binding.edLoginPassword.text.toString()

    override fun observerState(state: SignInState<String>) = with(binding) {
        when (state) {
            is SignInState.Loading -> loginProgress.visibility(state.loading)
            is SignInState.CheckEmail -> emailContainer.helperText = state.data
            is SignInState.CheckPassword -> passwordContainer.helperText = state.data
        }
    }

    override fun observerShared(actions: SignInAction) {
        when (actions) {
            is SignInAction.Navigate -> navigateDirections(actions.navigateTo)
            is SignInAction.ShowMessage -> showSnackBar(actions.message)
        }
    }
}
