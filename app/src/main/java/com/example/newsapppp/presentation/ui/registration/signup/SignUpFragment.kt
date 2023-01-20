package com.example.newsapppp.presentation.ui.registration.signup

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.databinding.FragmentSignUpBinding
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FirebaseState<String>, FragmentSignUpBinding, SignUpViewModel>(
    FragmentSignUpBinding::inflate
) {
    override val viewModel by viewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
    }

    override fun setupUi() = with(binding) {
        registerButton.setOnClickListener {
            viewModel.onSignUpClick(
                fullNameText(),
                emailText(),
                passwordText(),
            )
        }

        fullName.changesListener { isValid() }
        email.changesListener { isValid() }
        edPassword.changesListener { isValid() }
        confirmPassword.changesListener { isValid() }
        registerSignin.setOnClickListener { navigateTo(R.id.loginFragment) }
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


    override fun renderState(state: FirebaseState<String>) {
        with(binding) {
            when (state) {
                is FirebaseState.Loading -> {
                    loginProgress.invisible()
                }
                is FirebaseState.Failure -> {
                    loginProgress.visible()
                    showSnackBarString(requireView(), state.error)
                }
                is FirebaseState.Success -> {
                    loginProgress.visible()
                    showSnackBarString(requireView(), state.data)
                }
                is FirebaseState.Navigate -> {
                    navigateTo(state.navigateTo)
                    requireActivity().onBackPressedDispatcher.addCallback(requireActivity()) {
                        navigateTo(state.navigateTo)
                    }
                }
                is FirebaseState.CheckEmail -> {}
                is FirebaseState.CheckPassword -> {}
                is FirebaseState.CheckState -> {
                    fullNameContainer.helperText = state.name
                    emailContainer.helperText = state.email
                    passwordContainer.helperText = state.password
                    loginPasswordContainer.helperText = state.repeatPassword
                }
            }
        }
    }
}
