package com.example.newsapppp.presentation.ui.root

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentRootBinding
import com.example.newsapppp.presentation.extensions.internetConnectionDialog
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.muddassir.connection_checker.ConnectionState
import com.muddassir.connection_checker.ConnectivityListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootFragment : BaseFragment<RootState, FragmentRootBinding, RootViewModel>(
    FragmentRootBinding::inflate
), ConnectivityListener {

    override val viewModel by viewModels<RootViewModel>()

    override fun onClickListener() {}

    override fun observerState(state: RootState) {
        when (state) {
            is RootState.Navigation -> {
                binding.bottomNavigationView.setOnItemSelectedListener {
                    when (it.itemId) {
                        R.id.mainFragment -> navigate(state.mainFragment)
                        R.id.saveFragment -> navigate(state.saveFragment)
                        R.id.searchFragment -> navigate(state.searchFragment)
                    }
                    true
                }
            }
        }
    }

    override fun onConnectionState(state: ConnectionState) {
        when (state) {
            ConnectionState.CONNECTED -> internetConnectionDialog(getString(R.string.internet_connected))
            else -> internetConnectionDialog(getString(R.string.internet_disconnected))
        }
    }

    private fun navigate(fragment: Int) {
        binding.navFragment.findNavController().navigate(fragment)
    }
}
