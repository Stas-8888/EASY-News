package com.example.newsapppp.presentation.screens.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.ActivityMainBinding
import com.example.newsapppp.presentation.extensions.invisible
import com.example.newsapppp.presentation.extensions.visible
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
        setupNavController()
        observe()
    }

    private fun setupNavController() = with(binding) {
        slideUp(bottomNavigationView)
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
                        slideUp(bottomNavigationView)
                        bottomNavigationView.visible()
                    }
                    is MainActivityState.Failure -> {
                        bottomNavigationView.invisible()
                    }
                }
            }
        }
    }

    private fun slideUp(view: View) {
        val height = view.height
        ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, height.toFloat(), 0f).apply {
            duration = 1000
            start()
        }
    }
}
