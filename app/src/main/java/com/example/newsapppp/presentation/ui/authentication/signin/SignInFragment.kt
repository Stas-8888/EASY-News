package com.example.newsapppp.presentation.ui.authentication.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.newsapppp.databinding.FragmentLoginBinding
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<SignInState<String>, FragmentLoginBinding, SignInViewModel>(
    FragmentLoginBinding::inflate
) {
    override val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
    }

    override fun onClickListener() = with(binding) {
        btForgotPassword.setOnClickListener { viewModel.onForgotPasswordClicked() }
        btSkip.setOnClickListener { viewModel.onSkipClicked() }
        btLoginSignup.setOnClickListener { viewModel.onSkipSignUpClicked() }
        edLogin.changesListener {
            viewModel.isEmailChanged(emailText())
            hideKeyboard(requireActivity(), edLogin)
        }
        edLoginPassword.changesListener {
            viewModel.isPasswordChanged(passwordText())
            hideKeyboard(requireActivity(), edLoginPassword)
        }
        btSignIn.setOnClickListener { viewModel.signInButtonClicked(emailText(), passwordText()) }
    }

    private fun emailText(): String = binding.edLogin.text.toString()
    private fun passwordText(): String = binding.edLoginPassword.text.toString()

    override fun observerState(state: SignInState<String>) = with(binding) {
        when (state) {
            is SignInState.Loading -> loginProgress.invisible()
            is SignInState.Failure -> {
                loginProgress.visible()
                showSnackBarString(requireView(), state.error)
                loginProgress.invisible()
            }
            is SignInState.Success -> {
                loginProgress.visible()
                showSnackBarString(requireView(), state.data)
            }
            is SignInState.NavigateForgotPassword -> navigateTo(state.navigateForgotPassword)
            is SignInState.NavigateSkip -> navigateTo(state.navigateToSkip)
            is SignInState.NavigateSignUp -> navigateTo(state.navigateSignUp)
            is SignInState.Navigate -> navigateTo(state.navigateTo)
            is SignInState.CheckEmail -> emailContainer.helperText = state.data
            is SignInState.CheckPassword -> loginPasswordContainer.helperText = state.data
            is SignInState.CheckState -> {}
        }
    }
}
