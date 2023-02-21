package com.example.newsapppp.presentation.screens.authentication.forgotPassword

import androidx.fragment.app.viewModels
import com.example.newsapppp.databinding.FragmentForgotPasswordBinding
import com.example.newsapppp.presentation.extensions.showSnackBar
import com.example.newsapppp.presentation.extensions.textChangeListener
import com.example.newsapppp.presentation.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<ForgotPasswordState<String>, ForgotPasswordAction, FragmentForgotPasswordBinding, ForgotPasswordViewModel>(
        FragmentForgotPasswordBinding::inflate
    ) {

    override val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun onClickListener() = with(binding) {
        edEmail.textChangeListener { viewModel.isEmailChanged(emailText()) }
        btForgotPassword.setOnClickListener {
            viewModel.onForgotPasswordClicked(emailText())
        }
    }

    private fun emailText(): String = binding.edEmail.text.toString()

    override fun observerState(state: ForgotPasswordState<String>) {
        when (state) {
            is ForgotPasswordState.Loading -> {}
            is ForgotPasswordState.CheckEmail -> binding.emailContainer.helperText = state.data
        }
    }

    override fun observerShared(actions: ForgotPasswordAction) {
        when (actions) {
            is ForgotPasswordAction.ShowMessage -> showSnackBar(actions.message)
        }
    }
}
