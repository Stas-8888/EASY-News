package com.example.newsapppp.presentation.ui.splash

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.newsapppp.R
import com.example.newsapppp.presentation.utils.extensions.hideBottomNavigation
import com.example.newsapppp.presentation.utils.extensions.navigateTo
import com.example.newsapppp.databinding.FragmentSplashBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashState, FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::inflate
) {

    override val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
        viewModel.setupDayNightMode()
//        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorRed)
        val animationBounce = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
        binding.tvNews.startAnimation(animationBounce)
        binding.tvWelcome.startAnimation(animationBounce)

        lifecycleScope.launchWhenResumed {
            delay(TimeUnit.SECONDS.toMillis(3))
            navigateTo(R.id.loginFragment)
        }
    }

    override fun renderState(state: SplashState) {
        when (state) {
            is SplashState.Success -> {}
            is SplashState.Error -> {}
        }
    }
}
