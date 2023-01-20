package com.example.newsapppp.presentation.ui.root

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.work.*
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentRootBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.example.newsapppp.presentation.utils.MyWorker
import com.example.newsapppp.presentation.utils.extensions.internetConnectionDialog
import com.muddassir.connection_checker.ConnectionState
import com.muddassir.connection_checker.ConnectivityListener
import com.muddassir.connection_checker.checkConnection
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class RootFragment : BaseFragment<RootState, FragmentRootBinding, RootViewModel>(
    FragmentRootBinding::inflate
), ConnectivityListener {

    override val viewModel by viewModels<RootViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPeriodicWork()
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

    private fun myPeriodicWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val myRequest = PeriodicWorkRequest.Builder(
            MyWorker::class.java,
            15,
            TimeUnit.MINUTES
        ).setConstraints(constraints)
            .addTag("my_id")
            .build()

        //minimum interval is 15min, just wait 15 min,
        // I will cut this.. to show you
        //quickly

        //now is 0:15 let's wait until 0:30min

        WorkManager.getInstance(requireContext())
            .enqueueUniquePeriodicWork("my_id", ExistingPeriodicWorkPolicy.KEEP, myRequest)
    }

    private fun myOneTimeWork() {
        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()

        val myWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
    }

}
