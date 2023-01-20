package com.example.newsapppp.presentation.ui.registration.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapppp.R
import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.databinding.FragmentLoginBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.example.newsapppp.presentation.utils.extensions.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FirebaseState<String>, FragmentLoginBinding, LoginViewModel>(
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
//            viewModel.isValidEmail(emailText())
        }
        loginPassword.listenChanges {
//            viewModel.isValidPassword(passwordText())
        }
        btLogin.setOnClickListener {
            viewModel.signInClicked(
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
                binding.loginProgress.visible()
            }
            is FirebaseState.Failure -> {
                binding.loginProgress.invisible()
                showSnackBarString(requireView(), state.error)
            }
            is FirebaseState.Success -> {
                binding.loginProgress.invisible()
                showSnackBarString(requireView(), state.data)
            }
            is FirebaseState.Navigate -> navigateTo(state.navigateTo)


//            is LoginState.Loading -> {}
//            is LoginState.Success -> {
//                navigateTo(state.navigateTo)
//                snackBar(requireView(), state.successMessage)
//            }
//            is LoginState.CheckEmail -> binding.emailContainer.helperText = state.data
//            is LoginState.CheckPassword -> binding.loginPasswordContainer.helperText = state.data
//            is LoginState.Error -> snackBar(requireView(), state.message)
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
