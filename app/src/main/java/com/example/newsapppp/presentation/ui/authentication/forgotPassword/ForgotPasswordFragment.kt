package com.example.newsapppp.presentation.ui.authentication.forgotPassword

import androidx.fragment.app.viewModels
import com.example.newsapppp.databinding.FragmentForgotPasswordBinding
import com.example.newsapppp.presentation.extensions.changesListener
import com.example.newsapppp.presentation.extensions.navigateTo
import com.example.newsapppp.presentation.extensions.showSnackBarString
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<ForgotPasswordState<String>, FragmentForgotPasswordBinding, ForgotPasswordViewModel>(
        FragmentForgotPasswordBinding::inflate
    ) {

    override val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun onClickListener() = with(binding) {
        edLogin.changesListener { viewModel.isEmailChanged(emailText()) }
        btSignIn.setOnClickListener {
            viewModel.forgotPassword(emailText())
        }
    }

    private fun emailText(): String = binding.edLogin.text.toString()

    override fun observerState(state: ForgotPasswordState<String>) {
        when (state) {
            is ForgotPasswordState.Loading -> {}
            is ForgotPasswordState.Failure -> showSnackBarString(requireView(), state.error)
            is ForgotPasswordState.Success -> showSnackBarString(requireView(), state.data)
            is ForgotPasswordState.Navigate -> navigateTo(state.navigateTo)
            is ForgotPasswordState.CheckEmail -> binding.emailContainer.helperText = state.data
        }
    }
}
