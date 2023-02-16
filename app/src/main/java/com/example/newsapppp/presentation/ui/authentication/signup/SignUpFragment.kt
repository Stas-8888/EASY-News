package com.example.newsapppp.presentation.ui.authentication.signup

import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentSignUpBinding
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment :
    BaseFragment<SignUpState<String>, SignUpAction, FragmentSignUpBinding, SignUpViewModel>(
        FragmentSignUpBinding::inflate
    ) {
    override val viewModel by viewModels<SignUpViewModel>()

    override fun onClickListener() = with(binding) {
        registerButton.setOnClickListener {
            viewModel.signUnButtonClicked(
                fullNameText(),
                emailText(),
                passwordText(),
            )
        }

        fullName.changesListener { isValid() }
        email.changesListener { isValid() }
        edPassword.changesListener { isValid() }
        confirmPassword.changesListener { isValid() }
        registerSignin.setOnClickListener { navigateTo(R.id.signInFragment) }
    }

    override fun observerShared(actions: SignUpAction) {
    }

    private fun isValid() {
        viewModel.checkValidationFields(
            fullNameText(),
            emailText(),
            passwordText(),
            repeatPasswordText()
        )
    }

    private fun fullNameText(): String = binding.fullName.text.toString()
    private fun emailText(): String = binding.email.text.toString()
    private fun passwordText(): String = binding.edPassword.text.toString()
    private fun repeatPasswordText(): String = binding.confirmPassword.text.toString()


    override fun observerState(state: SignUpState<String>) {
        with(binding) {
            when (state) {
                is SignUpState.Loading -> {
                    loginProgress.invisible()
                }
                is SignUpState.Failure -> {
                    loginProgress.visible()
                    showSnackBarString(requireView(), state.error)
                }
                is SignUpState.Success -> {
                    loginProgress.visible()
                    showSnackBarString(requireView(), state.data)
                }
                is SignUpState.Navigate -> {
                    navigateTo(state.navigateTo)
                }
                is SignUpState.CheckState -> {
                    fullNameContainer.helperText = state.name
                    emailContainer.helperText = state.email
                    passwordContainer.helperText = state.password
                    loginPasswordContainer.helperText = state.repeatPassword
                }
            }
        }
    }
}
