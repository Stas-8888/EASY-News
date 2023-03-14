package com.example.newsapppp.presentation.screens.authentication.forgotPassword

import androidx.fragment.app.viewModels
import com.example.newsapppp.databinding.FragmentForgotPasswordBinding
import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.presentation.extensions.afterTextChanged
import com.example.newsapppp.presentation.extensions.hideKeyboard
import com.example.newsapppp.presentation.extensions.showInternetConnectionDialog
import com.example.newsapppp.presentation.extensions.showSnackBar
import com.example.newsapppp.presentation.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<ForgotPasswordState<String>, ForgotPasswordAction, FragmentForgotPasswordBinding, ForgotPasswordViewModel>(
        FragmentForgotPasswordBinding::inflate
    ) {

    override val viewModel by viewModels<ForgotPasswordViewModel>()

    override fun onClickListener() = with(binding) {
        edEmail.afterTextChanged { viewModel.isEmailChanged(it) }
        btForgotPassword.setOnClickListener {
            it.hideKeyboard()
            viewModel.onForgotPasswordClicked(UserModel(email = emailText()))
        }
    }

    private fun emailText(): String = binding.edEmail.text.toString()

    override fun observerState(state: ForgotPasswordState<String>) {
        when (state) {
            is ForgotPasswordState.Loading -> {}
            is ForgotPasswordState.CheckEmail -> binding.emailContainer.helperText = state.data
        }
    }

    override fun observerAction(actions: ForgotPasswordAction) {
        when (actions) {
            is ForgotPasswordAction.ShowMessage -> showSnackBar(actions.message)
            is ForgotPasswordAction.ShowNetworkDialog -> showInternetConnectionDialog(getString(actions.message))
        }
    }
}
