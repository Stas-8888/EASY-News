package com.example.newsapppp.presentation.ui.authentication.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.presentation.ui.authentication.AuthState
import com.example.newsapppp.databinding.FragmentLoginBinding
import com.example.newsapppp.presentation.extensions.*
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
        loginSignup.setOnClickListener { navigateTo(R.id.signUpFragment) }
        loginUsername.changesListener { viewModel.isValidEmail(emailText()) }
        loginPassword.changesListener { viewModel.isValidPassword(passwordText()) }
        btSignIn.setOnClickListener { viewModel.onSignInClicked(emailText(), passwordText()) }
    }

    private fun emailText(): String = binding.loginUsername.text.toString()
    private fun passwordText(): String = binding.loginPassword.text.toString()

    override fun setObservers(state: AuthState<String>) {
        when (state) {
            is AuthState.Loading -> binding.loginProgress.invisible()
            is AuthState.Failure -> {
                binding.loginProgress.visible()
                showSnackBarString(requireView(), state.error)
                binding.loginProgress.invisible()
            }
            is AuthState.Success -> {
                binding.loginProgress.visible()
                showSnackBarString(requireView(), state.data)
            }
            is AuthState.Navigate -> navigateTo(state.navigateTo)
            is AuthState.CheckEmail -> binding.emailContainer.helperText = state.data
            is AuthState.CheckPassword -> binding.loginPasswordContainer.helperText = state.data
            is AuthState.CheckState -> {}
        }
    }
}
