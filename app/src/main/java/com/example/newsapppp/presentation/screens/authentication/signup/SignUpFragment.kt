package com.example.newsapppp.presentation.screens.authentication.signup

import androidx.fragment.app.viewModels
import com.example.newsapppp.databinding.FragmentSignUpBinding
import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.presentation.extensions.invisible
import com.example.newsapppp.presentation.extensions.navigateDirections
import com.example.newsapppp.presentation.extensions.showSnackBar
import com.example.newsapppp.presentation.extensions.afterTextChanged
import com.example.newsapppp.presentation.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment :
    BaseFragment<SignUpState<String>, SignUpAction, FragmentSignUpBinding, SignUpViewModel>(
        FragmentSignUpBinding::inflate
    ) {
    override val viewModel by viewModels<SignUpViewModel>()

    override fun onClickListener() = with(binding) {
        btGoogle.setOnClickListener {
            viewModel.notEnabledYet()
        }
        btFaceBook.setOnClickListener {
            viewModel.notEnabledYet()
        }
        registerButton.setOnClickListener {
            viewModel.onSignUnButtonClicked(
                UserModel(email = emailText(), password = passwordText())
            )
        }

        fullName.afterTextChanged { isValid() }
        email.afterTextChanged { isValid() }
        edPassword.afterTextChanged { isValid() }
        confirmPassword.afterTextChanged { isValid() }
        btSignIn.setOnClickListener { viewModel.onSignInBottomClicked() }
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
                is SignUpState.CheckState -> {
                    fullNameContainer.helperText = state.name
                    emailContainer.helperText = state.email
                    passwordContainer.helperText = state.password
                    passwordContainer.helperText = state.repeatPassword
                }
            }
        }
    }

    override fun observerShared(actions: SignUpAction) {
        when (actions) {
            is SignUpAction.Navigate -> navigateDirections(actions.navigateTo)
            is SignUpAction.ShowMessage -> showSnackBar(actions.message)
        }
    }
}
