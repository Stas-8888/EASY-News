package com.example.newsapppp.presentation.screens.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.ActivityMainBinding
import com.example.newsapppp.presentation.extensions.makeGone
import com.example.newsapppp.presentation.extensions.makeVisible
import com.example.newsapppp.presentation.extensions.slideDownAnimation
import com.example.newsapppp.presentation.extensions.slideUpAnimation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        observe()
    }

    private fun setupBottomNavigation() = with(binding) {
        val navController = findNavController(R.id.nav_fragment)
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            viewModel.setupBottomNavigation(destination)
        }
    }

    private fun observe() = lifecycleScope.launchWhenStarted {
        with(binding) {
            viewModel.state.collectLatest {
                when (it) {
                    is MainActivityState.Success -> {
                        bottomNavigationView.slideUpAnimation()
                        bottomNavigationView.makeVisible()
                    }
                    is MainActivityState.Failure -> {
                        bottomNavigationView.slideDownAnimation()
                        bottomNavigationView.makeGone()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
