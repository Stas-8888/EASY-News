package com.example.newsapppp.presentation.ui.root

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.work.*
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentRootBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.example.newsapppp.presentation.ui.registration.login.LoginState
import com.example.newsapppp.presentation.ui.registration.login.LoginViewModel
import com.example.newsapppp.presentation.utils.MyWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class RootFragment : BaseFragment<LoginState, FragmentRootBinding, LoginViewModel>(
    FragmentRootBinding::inflate
) {
    override val viewModel by viewModels<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomNavListener()
        myPeriodicWork()
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

    private fun navigate(fragment: Int) {
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
