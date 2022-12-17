package com.example.newsapppp.presentation.ui.registration.sign

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentSignUpBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.example.newsapppp.core.extensions.listenChanges
import com.example.newsapppp.core.extensions.navigateTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<SignUpState, FragmentSignUpBinding, SignUpViewModel>(
    FragmentSignUpBinding::inflate
) {
    override val viewModel by viewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() = with(binding) {
        registerButton.setOnClickListener {
            viewModel.signUpUser(
                fullNameText(),
                emailText(),
                passwordText(),
                repeatPasswordText(),
                navigateTo(R.id.loginFragment)
            )
        }

        binding.fullName.listenChanges {
            isValid()
        }
        binding.email.listenChanges {
            isValid()
        }
        binding.edPassword.listenChanges {
            isValid()
        }
        binding.confirmPassword.listenChanges {
            isValid()
        }
    }

    private fun isValid() {
        viewModel.isValidate(fullNameText(), emailText(), passwordText(), repeatPasswordText())
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
            is SignUpState.CheckState -> {
                fullNameContainer.helperText = state.name
                emailContainer.helperText = state.email
                passwordContainer.helperText = state.password
                loginPasswordContainer.helperText = state.repeatPassword
            }
            is SignUpState.Error -> {}
        }
    }
}
