package com.example.newsapppp.presentation.ui.root

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentRootBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.example.newsapppp.presentation.extensions.internetConnectionDialog
import com.muddassir.connection_checker.ConnectionState
import com.muddassir.connection_checker.ConnectivityListener
import com.muddassir.connection_checker.checkConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootFragment : BaseFragment<RootState, FragmentRootBinding, RootViewModel>(
    FragmentRootBinding::inflate
), ConnectivityListener {

    override val viewModel by viewModels<RootViewModel>()

    override fun setupUi() {
        checkConnection(this)
    }

    private fun navigate(fragment: Int) {
        binding.navFragment.findNavController().navigate(fragment)
    }

    override fun renderState(state: RootState) {
        when (state) {
            is RootState.Loading -> {}
            is RootState.Error -> {}
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
            ConnectionState.CONNECTED -> {
                internetConnectionDialog(getString(R.string.internet_connected))
            }
            ConnectionState.SLOW -> {
                internetConnectionDialog(getString(R.string.slow_internet))
            }
            else -> {
                internetConnectionDialog(getString(R.string.internet_disconnected))
            }
        }
    }
}
