package com.example.newsapppp.presentation.ui.root

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
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
//        setBottomNavListener()
//        binding.navFragment.findNavController().navigate(R.id.nav_fragment)
        
    }

//    private fun setBottomNavListener() {
//        binding.bottomNavigationView.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.mainFragment -> navigateTo(R.id.mainFragment)
//                R.id.saveFragment -> navigateTo(R.id.saveFragment)
//                R.id.searchFragment -> navigateTo(R.id.searchFragment)
//            }
//            true
//        }
//    }

    override fun renderState(state: LoginState) {
        when (state) {
            is LoginState.Success -> {}
            is LoginState.CheckState -> {}
            is LoginState.Error -> {}
        }
    }
}
