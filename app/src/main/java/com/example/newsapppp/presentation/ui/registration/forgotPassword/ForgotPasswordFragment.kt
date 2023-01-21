package com.example.newsapppp.presentation.ui.registration.forgotPassword

import androidx.fragment.app.viewModels
import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.databinding.FragmentForgotPasswordBinding
import com.example.newsapppp.presentation.extensions.changesListener
import com.example.newsapppp.presentation.extensions.navigateTo
import com.example.newsapppp.presentation.extensions.showSnackBarString
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<FirebaseState<String>, FragmentForgotPasswordBinding, ForgotPasswordViewModel>(
        FragmentForgotPasswordBinding::inflate
    ) {
    override val viewModel by viewModels<ForgotPasswordViewModel>()
    override fun setupUi() = with(binding) {
        loginUsername.changesListener { viewModel.isValidEmail(emailText()) }
        btSignIn.setOnClickListener {
            viewModel.forgotPassword(emailText())
        }
    }

    private fun emailText(): String = binding.loginUsername.text.toString()

    override fun renderState(state: FirebaseState<String>) {
        when (state) {
            is FirebaseState.Loading -> {}
            is FirebaseState.Failure -> {
                showSnackBarString(requireView(), state.error)
            }
            is FirebaseState.Success -> {
                showSnackBarString(requireView(), state.data)
            }
            is FirebaseState.Navigate -> navigateTo(state.navigateTo)
            is FirebaseState.CheckEmail -> binding.emailContainer.helperText = state.data
            is FirebaseState.CheckPassword -> {}
            is FirebaseState.CheckState -> {}
        }
    }
}
