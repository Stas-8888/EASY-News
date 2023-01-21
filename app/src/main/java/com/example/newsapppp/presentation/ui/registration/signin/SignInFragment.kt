package com.example.newsapppp.presentation.ui.registration.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.databinding.FragmentLoginBinding
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_forgot_password.*

@AndroidEntryPoint
class SignInFragment : BaseFragment<FirebaseState<String>, FragmentLoginBinding, SignInViewModel>(
    FragmentLoginBinding::inflate
) {
    override val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
    }

    override fun setupUi() = with(binding) {
        btForgotPassword.setOnClickListener { navigateTo(R.id.forgotPasswordFragment) }
        btSkip.setOnClickListener { navigateTo(R.id.mainFragment) }
        loginSignup.setOnClickListener { navigateTo(R.id.signUpFragment) }
        loginUsername.changesListener { viewModel.isValidEmail(emailText()) }
        loginPassword.changesListener { viewModel.isValidPassword(passwordText()) }
        btSignIn.setOnClickListener { viewModel.onSignInClicked(emailText(), passwordText()) }
    }

    private fun emailText(): String = binding.loginUsername.text.toString()
    private fun passwordText(): String = binding.loginPassword.text.toString()

    override fun renderState(state: FirebaseState<String>) {
        when (state) {
            is FirebaseState.Loading -> binding.loginProgress.invisible()
            is FirebaseState.Failure -> {
                binding.loginProgress.visible()
                showSnackBarString(requireView(), state.error)
                binding.loginProgress.invisible()
            }
            is FirebaseState.Success -> {
                binding.loginProgress.visible()
                showSnackBarString(requireView(), state.data)
            }
            is FirebaseState.Navigate -> navigateTo(state.navigateTo)
            is FirebaseState.CheckEmail -> binding.emailContainer.helperText = state.data
            is FirebaseState.CheckPassword -> binding.loginPasswordContainer.helperText = state.data
            is FirebaseState.CheckState -> {}
        }
    }
}
