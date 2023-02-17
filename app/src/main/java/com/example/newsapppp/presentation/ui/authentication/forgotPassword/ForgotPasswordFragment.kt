package com.example.newsapppp.presentation.ui.authentication.forgotPassword

import androidx.fragment.app.viewModels
import com.example.newsapppp.databinding.FragmentForgotPasswordBinding
import com.example.newsapppp.presentation.extensions.changesListener
import com.example.newsapppp.presentation.extensions.showSnackBarString
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<ForgotPasswordState<String>, ForgotPasswordAction, FragmentForgotPasswordBinding, ForgotPasswordViewModel>(
        FragmentForgotPasswordBinding::inflate
    ) {

    override val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun onClickListener() = with(binding) {
        edEmail.changesListener { viewModel.isEmailChanged(emailText()) }
        btForgotPassword.setOnClickListener {
            viewModel.onForgotPasswordClicked(emailText())
        }
    }

    private fun emailText(): String = binding.edEmail.text.toString()

    override fun observerState(state: ForgotPasswordState<String>) {
        when (state) {
            is ForgotPasswordState.Loading -> {}
            is ForgotPasswordState.Success -> showSnackBarString(requireView(), state.data)
            is ForgotPasswordState.CheckEmail -> binding.emailContainer.helperText = state.data
        }
    }

    override fun observerShared(actions: ForgotPasswordAction) {
        when (actions) {
            is ForgotPasswordAction.Message -> showSnackBarString(requireView(), actions.message)

        }
    }
}
