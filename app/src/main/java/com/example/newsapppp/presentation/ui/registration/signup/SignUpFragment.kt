package com.example.newsapppp.presentation.ui.registration.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.core.FirebaseState
import com.example.newsapppp.databinding.FragmentSignUpBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.example.newsapppp.presentation.utils.extensions.hideBottomNavigation
import com.example.newsapppp.presentation.utils.extensions.listenChanges
import com.example.newsapppp.presentation.utils.extensions.navigateTo
import com.example.newsapppp.presentation.utils.extensions.showSnackBarString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FirebaseState<String>, FragmentSignUpBinding, SignUpViewModel>(
    FragmentSignUpBinding::inflate
) {
    override val viewModel by viewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() = with(binding) {
        registerButton.setOnClickListener {
            viewModel.signUpUser(
                fullNameText(),
                emailText(),
                passwordText(),
                repeatPasswordText(),
            )
        }

        fullName.listenChanges {
            isValid()
        }
        email.listenChanges {
            isValid()
        }
        edPassword.listenChanges {
            isValid()
        }
        confirmPassword.listenChanges {
            isValid()
        }
        registerSignin.setOnClickListener {
            navigateTo(R.id.loginFragment)
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

    override fun renderState(state: FirebaseState<String>) = with(binding) {
        when (state) {
            is FirebaseState.Loading -> {
//                binding.loginProgress.invisible()
            }
            is FirebaseState.Failure -> {
//                binding.loginProgress.visible()
                showSnackBarString(requireView(), state.error)
//                binding.loginProgress.invisible()
            }
            is FirebaseState.Success -> {
//                binding.loginProgress.visible()
                showSnackBarString(requireView(), state.data)
            }
            is FirebaseState.Navigate -> navigateTo(state.navigateTo)
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
