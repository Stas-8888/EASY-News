package com.example.newsapppp.presentation.screens.authentication.signin

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.newsapppp.presentation.extensions.clickAnimation
import com.example.newsapppp.presentation.extensions.hideKeyboard
import com.example.newsapppp.presentation.extensions.navigateDirections
import com.example.newsapppp.presentation.extensions.shakeAnimation
import com.example.newsapppp.presentation.extensions.showInternetConnectionDialog
import com.example.newsapppp.presentation.extensions.showSnackBar
import com.example.newsapppp.presentation.extensions.visibility
import com.example.newsapppp.databinding.FragmentSignInBinding
import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.presentation.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment :
    BaseFragment<SignInState<String>, SignInAction, FragmentSignInBinding, SignInViewModel>(
        FragmentSignInBinding::inflate
    ) {
    override val viewModel by viewModels<SignInViewModel>()

    override fun onClickListener() = with(binding) {
        edLogin.addTextChangedListener {
            viewModel.isEmailChanged(it.toString())
        }
        edLoginPassword.addTextChangedListener {
            viewModel.isPasswordChanged(it.toString())
        }
        btSignIn.setOnClickListener {
            it.shakeAnimation()
            it.hideKeyboard()
            viewModel.onSignInButtonClicked(
                UserModel(email = emailText(), password = passwordText())
            )
        }
        btForgotPassword.setOnClickListener {
            it.hideKeyboard()
            viewModel.onForgotPasswordButtonClicked()
        }
        btSignUp.setOnClickListener {
            it.clickAnimation()
            it.hideKeyboard()
            viewModel.onSignUpButtonClicked()
        }
        btSkip.setOnClickListener {
            it.clickAnimation()
            it.hideKeyboard()
            viewModel.onSkipButtonClicked()
        }
    }

    private fun emailText(): String = binding.edLogin.text.toString().trim()
    private fun passwordText(): String = binding.edLoginPassword.text.toString().trim()

    override fun observerState(state: SignInState<String>) = with(binding) {
        when (state) {
            is SignInState.Loading -> loginProgress.visibility(state.loading)
            is SignInState.CheckEmail -> emailContainer.helperText = state.data
            is SignInState.CheckPassword -> passwordContainer.helperText = state.data
        }
    }

    override fun observerAction(actions: SignInAction) {
        when (actions) {
            is SignInAction.Navigate -> navigateDirections(actions.navigateTo)
            is SignInAction.ShowMessage -> showSnackBar(actions.message)
            is SignInAction.ShowNetworkDialog -> showInternetConnectionDialog(getString(actions.message))
        }
    }
}
