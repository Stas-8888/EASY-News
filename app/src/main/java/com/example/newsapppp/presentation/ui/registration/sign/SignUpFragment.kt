package com.example.newsapppp.presentation.ui.registration.sign

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentSignUpBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.example.newsapppp.presentation.utils.extensions.listenChanges
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignUpState, FragmentSignUpBinding, SignUpViewModel>(
    FragmentSignUpBinding::inflate
) {
    override val viewModel by viewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailFocusListener()
        passwordFocusListener()
        fullNameFocusListener()
        repeatPasswordFocusListener()

        binding.registerButton.setOnClickListener {
            createNewUser()
        }
    }

    private fun fullNameFocusListener() {
        binding.fullName.listenChanges {
            binding.fullNameContainer.helperText = viewModel.checkFullName(fullNameText())
        }
    }

    private fun emailFocusListener() {
        binding.email.listenChanges {
            binding.emailContainer.helperText = viewModel.checkEmail(emailText())
        }
    }

    private fun passwordFocusListener() {
        binding.edPassword.listenChanges {
            binding.passwordContainer.helperText = viewModel.checkPassword(passwordText())
        }
    }

    private fun repeatPasswordFocusListener() {
        binding.confirmPassword.listenChanges {
            binding.loginPasswordContainer.helperText =
                viewModel.checkRepeatedPassword(passwordText(), repeatPasswordText())
        }
    }

    private fun createNewUser() = with(binding) {
        emailContainer.helperText = viewModel.checkEmail(emailText())
        fullNameContainer.helperText = viewModel.checkFullName(fullNameText())
        passwordContainer.helperText = viewModel.checkPassword(passwordText())
        loginPasswordContainer.helperText =
            viewModel.checkRepeatedPassword(passwordText(), repeatPasswordText())

        val validFullName = fullNameContainer.helperText == getString(R.string.successful)
        val validEmail = emailContainer.helperText == getString(R.string.successful)
        val validPassword = passwordContainer.helperText == getString(R.string.successful)
        val validRepeatPassword = viewModel.checkRepeatedPassword(
            passwordText(), repeatPasswordText()
        ) == getString(R.string.successful)

        if (validFullName && validEmail && validPassword && validRepeatPassword) {
            viewModel.signUpUser(
                fullNameText(),
                emailText(),
                passwordText(),
                navigateTo(R.id.loginFragment)
            )
        }
    }

    private fun fullNameText(): String {
        return binding.fullName.text.toString()
    }

    private fun emailText(): String {
        return binding.email.text.toString()
    }

    private fun passwordText(): String {
        return binding.edPassword.text.toString()
    }

    private fun repeatPasswordText(): String {
        return binding.confirmPassword.text.toString()
    }

    override fun renderState(state: SignUpState) = with(binding) {
        when (state) {
            is SignUpState.Success -> {}
            is SignUpState.Error -> {}
        }
    }
}
