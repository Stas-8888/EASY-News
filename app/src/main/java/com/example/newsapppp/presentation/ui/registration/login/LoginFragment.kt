package com.example.newsapppp.presentation.ui.registration.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentLoginBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.example.newsapppp.presentation.utils.extensions.listenChanges
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginState, FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate
) {
    override val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            viewModel.isValidate(
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
            is LoginState.Success -> {}
            is LoginState.CheckState -> {
                binding.emailContainer.helperText = state.data
                binding.loginPasswordContainer.helperText = state.data
            }
            is LoginState.Error -> {
                toast("Enter correct Email and Password")
            }
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
