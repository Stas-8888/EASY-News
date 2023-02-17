package com.example.newsapppp.presentation.ui.authentication.signin

import androidx.fragment.app.viewModels
import com.example.newsapppp.databinding.FragmentSignInBinding
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment :
    BaseFragment<SignInState<String>, SignInAction, FragmentSignInBinding, SignInViewModel>(
        FragmentSignInBinding::inflate
    ) {
    override val viewModel by viewModels<SignInViewModel>()

    override fun onClickListener() = with(binding) {
        edLogin.changesListener {
            viewModel.isEmailChanged(emailText())
            hideKeyboard(requireActivity(), edLogin)
        }
        edLoginPassword.changesListener {
            viewModel.isPasswordChanged(passwordText())
            hideKeyboard(requireActivity(), edLoginPassword)
        }
        btSignIn.setOnClickListener {
            viewModel.onSignInButtonClicked(emailText(), passwordText())
        }
        btForgotPassword.setOnClickListener {
            viewModel.onForgotPasswordClicked()
        }
        btLoginSignup.setOnClickListener {
            viewModel.onSignUpClicked()
        }
        btSkip.setOnClickListener {
            viewModel.onSkipClicked()
        }
    }

    private fun emailText(): String = binding.edLogin.text.toString()
    private fun passwordText(): String = binding.edLoginPassword.text.toString()

    override fun observerState(state: SignInState<String>) = with(binding) {
        when (state) {
            is SignInState.Loading -> loginProgress.invisible()
            is SignInState.Success -> {
                loginProgress.visible()
                showSnackBarString(requireView(), state.data)
            }
            is SignInState.CheckEmail -> emailContainer.helperText = state.data
            is SignInState.CheckPassword -> loginPasswordContainer.helperText = state.data
        }
    }

    override fun observerShared(actions: SignInAction) {
        when (actions) {
            is SignInAction.Navigate -> navigateDirections(actions.navigateTo)
            is SignInAction.Message -> showSnackBarString(requireView(), actions.message)
        }
    }
}
