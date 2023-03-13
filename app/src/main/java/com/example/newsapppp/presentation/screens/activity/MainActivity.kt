package com.example.newsapppp.presentation.screens.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.ActivityMainBinding
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.extensions.isGone
import com.example.newsapppp.presentation.extensions.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
                        bottomNavigationView.slideUp()
                        bottomNavigationView.isVisible()
                    }
                    is MainActivityState.Failure -> {
                        bottomNavigationView.slideDown()
                        bottomNavigationView.isGone()
                    }
                }
            }
        }
    }
}
