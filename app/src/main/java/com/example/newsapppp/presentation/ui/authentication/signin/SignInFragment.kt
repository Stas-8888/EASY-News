package com.example.newsapppp.presentation.ui.authentication.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentLoginBinding
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.ui.authentication.AuthState
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<AuthState<String>, FragmentLoginBinding, SignInViewModel>(
    FragmentLoginBinding::inflate
) {
    override val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
    }

    override fun onClickListener() = with(binding) {
        btForgotPassword.setOnClickListener { navigateTo(R.id.forgotPasswordFragment) }
        btSkip.setOnClickListener { navigateTo(R.id.mainFragment) }
        btLoginSignup.setOnClickListener { navigateTo(R.id.signUpFragment) }
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

    override fun observerState(state: AuthState<String>) = with(binding){
        when (state) {
            is AuthState.Loading -> loginProgress.invisible()
            is AuthState.Failure -> {
                loginProgress.visible()
                showSnackBarString(requireView(), state.error)
                loginProgress.invisible()
            }
            is AuthState.Success -> {
                loginProgress.visible()
                showSnackBarString(requireView(), state.data)
            }
            is AuthState.Navigate -> navigateTo(state.navigateTo)
            is AuthState.CheckEmail -> emailContainer.helperText = state.data
            is AuthState.CheckPassword -> loginPasswordContainer.helperText = state.data
            is AuthState.CheckState -> {}
        }
    }
}
