package com.example.newsapppp.presentation.ui.root

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentRootBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.example.newsapppp.presentation.ui.registration.login.LoginState
import com.example.newsapppp.presentation.ui.registration.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootFragment : BaseFragment<LoginState, FragmentRootBinding, LoginViewModel>(
    FragmentRootBinding::inflate
) {
    override val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomNavListener()
    }

    private fun setBottomNavListener() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mainFragment -> navigate(R.id.mainFragment)
                R.id.saveFragment -> navigate(R.id.saveFragment)
                R.id.searchFragment -> navigate(R.id.searchFragment)
            }
            true
        }
    }

    private fun navigate(fragment: Int){
        binding.navFragment.findNavController().navigate(fragment)
    }

    override fun renderState(state: LoginState) {
        when (state) {
            is LoginState.Loading -> {}
            is LoginState.Success -> {}
            is LoginState.CheckEmail -> {}
            is LoginState.CheckPassword -> {}
            is LoginState.Error -> {}
        }
    }
}
