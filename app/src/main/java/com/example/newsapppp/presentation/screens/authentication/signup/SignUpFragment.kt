package com.example.newsapppp.presentation.screens.authentication.signup

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.newsapppp.databinding.FragmentSignUpBinding
import com.example.newsapppp.domain.model.UserModel
import com.example.newsapppp.presentation.extensions.makeGone
import com.example.newsapppp.presentation.extensions.navigateDirections
import com.example.newsapppp.presentation.extensions.showInternetConnectionDialog
import com.example.newsapppp.presentation.extensions.showSnackBar
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
            viewModel.onNotEnabledBottomClicked()
        }
        btFaceBook.setOnClickListener {
            viewModel.onNotEnabledBottomClicked()
        }
        registerButton.setOnClickListener {
            viewModel.onSignUnButtonClicked(
                UserModel(email = emailText(), password = passwordText())
            )
        }

        fullName.addTextChangedListener { isValid() }
        email.addTextChangedListener { isValid() }
        edPassword.addTextChangedListener { isValid() }
        confirmPassword.addTextChangedListener { isValid() }
        btSignIn.setOnClickListener { viewModel.navigateToSignInScreen() }
    }

    private fun isValid() {
        viewModel.checkValidationFields(
            UserModel(
                user = fullNameText(),
                email = emailText(),
                password = passwordText(),
                repeatedPassword = repeatPasswordText()
            )
        )
    }

    private fun fullNameText(): String = binding.fullName.text.toString().trim()
    private fun emailText(): String = binding.email.text.toString().trim()
    private fun passwordText(): String = binding.edPassword.text.toString().trim()
    private fun repeatPasswordText(): String = binding.confirmPassword.text.toString().trim()


    override fun observerState(state: SignUpState<String>) {
        with(binding) {
            when (state) {
                is SignUpState.Loading -> {
                    loginProgress.makeGone()
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

    override fun observerAction(actions: SignUpAction) {
        when (actions) {
            is SignUpAction.Navigate -> navigateDirections(actions.navigateTo)
            is SignUpAction.ShowMessage -> showSnackBar(actions.message)
            is SignUpAction.ShowNetworkDialog -> showInternetConnectionDialog(getString(actions.message))
        }
    }
}
