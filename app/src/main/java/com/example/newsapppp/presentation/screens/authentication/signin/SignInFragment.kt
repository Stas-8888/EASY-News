package com.example.newsapppp.presentation.screens.authentication.signin

import android.view.animation.TranslateAnimation
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
        binding.appTitle.startAnimation(TranslateAnimation(0f, 0f, -160f, 0f).apply {
            duration = 1000
        })
        edLogin.textChangeListener {
            viewModel.isEmailChanged(emailText())
            hideKeyboard(requireActivity(), edLogin)
        }
        edLoginPassword.textChangeListener {
            viewModel.isPasswordChanged(passwordText())
            hideKeyboard(requireActivity(), edLoginPassword)
        }
        btSignIn.setOnClickListener {
            viewModel.onSignInButtonClicked(emailText(), passwordText())
        }
        btForgotPassword.setOnClickListener {
            viewModel.onForgotPasswordButtonClicked()
        }
        btSignUp.setOnClickListener {
            viewModel.onSignUpButtonClicked()
        }
        btSkip.setOnClickListener {
            viewModel.onSkipButtonClicked()
        }
    }

    private fun emailText(): String = binding.edLogin.text.toString()
    private fun passwordText(): String = binding.edLoginPassword.text.toString()

    override fun observerState(state: SignInState<String>) = with(binding) {
        when (state) {
            is SignInState.Loading -> loginProgress.visibility(state.loading)
            is SignInState.CheckEmail -> emailContainer.helperText = state.data
            is SignInState.CheckPassword -> loginPasswordContainer.helperText = state.data
        }
    }

    override fun observerShared(actions: SignInAction) {
        when (actions) {
            is SignInAction.Navigate -> navigateDirections(actions.navigateTo)
            is SignInAction.ShowMessage -> showSnackBar(actions.message)
        }
    }
}
