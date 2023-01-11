package com.example.newsapppp.presentation.ui.registration.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.presentation.utils.extensions.hideBottomNavigation
import com.example.newsapppp.presentation.utils.extensions.listenChanges
import com.example.newsapppp.presentation.utils.extensions.navigateTo
import com.example.newsapppp.presentation.utils.extensions.snackBar
import com.example.newsapppp.databinding.FragmentLoginBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginState, FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate
) {
    override val viewModel by viewModels<LoginViewModel>()

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
        loginUsername.listenChanges {
            viewModel.isValidEmail(emailText())
        }
        loginPassword.listenChanges {
            viewModel.isValidPassword(passwordText())
        }
        btLogin.setOnClickListener {
            viewModel.signInClicked(
                emailText(),
                passwordText()
            ) { navigateTo(R.id.mainFragment) }
        }
    }

    private fun emailText(): String {
        return binding.loginUsername.text.toString()
    }

    private fun passwordText(): String {
        return binding.loginPassword.text.toString()
    }

    override fun renderState(state: LoginState) {
        when (state) {
            is LoginState.Loading -> {}
            is LoginState.Success -> snackBar(requireView(), state.success)
            is LoginState.CheckEmail -> binding.emailContainer.helperText = state.data
            is LoginState.CheckPassword -> binding.loginPasswordContainer.helperText = state.data
            is LoginState.Error -> snackBar(requireView(), state.message)
        }
    }

//    override fun onStart() {
//        super.onStart()
//
//        if(firebaseAuth.currentUser != null){
//            navigateTo(R.id.mainFragment)
//        }
//    }
}
