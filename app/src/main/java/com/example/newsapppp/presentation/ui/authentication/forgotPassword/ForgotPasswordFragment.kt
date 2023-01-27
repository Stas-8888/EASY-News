package com.example.newsapppp.presentation.ui.authentication.forgotPassword

import androidx.fragment.app.viewModels
import com.example.newsapppp.databinding.FragmentForgotPasswordBinding
import com.example.newsapppp.presentation.extensions.changesListener
import com.example.newsapppp.presentation.extensions.navigateTo
import com.example.newsapppp.presentation.extensions.showSnackBarString
import com.example.newsapppp.presentation.ui.authentication.AuthState
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<AuthState<String>, FragmentForgotPasswordBinding, ForgotPasswordViewModel>(
        FragmentForgotPasswordBinding::inflate
    ) {

    override val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun onClickListener() = with(binding) {
        loginUsername.changesListener { viewModel.isValidEmail(emailText()) }
        btSignIn.setOnClickListener {
            viewModel.forgotPassword(emailText())
        }
    }

    private fun emailText(): String = binding.loginUsername.text.toString()

    override fun setObservers(state: AuthState<String>) {
        when (state) {
            is AuthState.Loading -> {}
            is AuthState.Failure -> {
                showSnackBarString(requireView(), state.error)
            }
            is AuthState.Success -> {
                showSnackBarString(requireView(), state.data)
            }
            is AuthState.Navigate -> navigateTo(state.navigateTo)
            is AuthState.CheckEmail -> binding.emailContainer.helperText = state.data
            is AuthState.CheckPassword -> {}
            is AuthState.CheckState -> {}
        }
    }
}
