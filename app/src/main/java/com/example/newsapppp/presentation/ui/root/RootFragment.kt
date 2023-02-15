package com.example.newsapppp.presentation.ui.root

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentRootBinding
import com.example.newsapppp.presentation.extensions.internetConnectionDialog
import com.example.newsapppp.presentation.extensions.navigateTo
import com.example.newsapppp.presentation.extensions.showSnackBarString
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.muddassir.connection_checker.ConnectionState
import com.muddassir.connection_checker.ConnectivityListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootFragment : BaseFragment<RootState, FragmentRootBinding, RootViewModel>(
    FragmentRootBinding::inflate
), ConnectivityListener {

    override val viewModel by viewModels<RootViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.interceptorErrors()
        viewModel.checkRegistration()
    }

//    fun onBackPressed() {
//        binding.navFragment.findNavController().navigateUp()
//    }

    override fun onClickListener() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            viewModel.setupBottomNavigationClick(it)
            true
        }
    }

    override fun observerState(state: RootState) {
        when (state) {
            is RootState.NavigationToMain -> navigateTo(state.mainFragment)
            is RootState.InterceptorErrors -> showSnackBarString(requireView(), state.error)
            is RootState.Navigation -> navigateTo(state.navigateFragment)
        }
    }

    override fun onConnectionState(state: ConnectionState) {
        when (state) {
            ConnectionState.CONNECTED -> internetConnectionDialog(getString(R.string.internet_connected))
            else -> internetConnectionDialog(getString(R.string.internet_disconnected))
        }
    }

//    private fun navigate(fragment: Int) {
//        binding.navFragment.findNavController().navigate(fragment)
//    }
}
