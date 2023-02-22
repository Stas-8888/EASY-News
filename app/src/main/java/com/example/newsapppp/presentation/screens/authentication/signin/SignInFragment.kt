package com.example.newsapppp.presentation.screens.authentication.signin

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiAnimation()
    }

    override fun onClickListener() = with(binding) {
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
            is SignInState.CheckPassword -> passwordContainer.helperText = state.data
        }
    }

    override fun observerShared(actions: SignInAction) {
        when (actions) {
            is SignInAction.Navigate -> navigateDirections(actions.navigateTo)
            is SignInAction.ShowMessage -> showSnackBar(actions.message)
        }
    }

    private fun setupUiAnimation() = with(binding) {
        val animAlpha = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        emailContainer.startAnimation(animAlpha)
        passwordContainer.startAnimation(animAlpha)
        btSignIn.startAnimation(animAlpha)
        titleIcon.startAnimation(animAlpha)
        appTitle.startAnimation(animAlpha)
    }
}
