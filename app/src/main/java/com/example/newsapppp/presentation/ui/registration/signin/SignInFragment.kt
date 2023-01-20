package com.example.newsapppp.presentation.ui.registration.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.databinding.FragmentLoginBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.example.newsapppp.presentation.utils.extensions.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FirebaseState<String>, FragmentLoginBinding, SignInViewModel>(
    FragmentLoginBinding::inflate
) {
    override val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() = with(binding) {
        btSkip.setOnClickListener {
            navigateTo(R.id.mainFragment)
        }
        loginSignup.setOnClickListener {
            navigateTo(R.id.signUpFragment)
        }
        loginUsername.changesListener {
            viewModel.isValidEmail(emailText())
        }
        loginPassword.changesListener {
            viewModel.isValidPassword(passwordText())
        }
        btLogin.setOnClickListener {
            viewModel.onSignInClicked(
                emailText(),
                passwordText()
            )
        }
    }

    private fun emailText(): String {
        return binding.loginUsername.text.toString()
    }

    private fun passwordText(): String {
        return binding.loginPassword.text.toString()
    }

    override fun renderState(state: FirebaseState<String>) {
        when (state) {
            is FirebaseState.Loading -> {
                binding.loginProgress.invisible()
            }
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
