package com.example.newsapppp.presentation.ui.splash

import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentSplashBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.example.newsapppp.presentation.extensions.hideBottomNavigation
import com.example.newsapppp.presentation.extensions.navigateTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashState, FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::inflate
) {
    override val viewModel by viewModels<SplashViewModel>()

    override fun setupUi() = with(binding) {
        hideBottomNavigation()
        viewModel.setupDayNightMode()
        viewModel.delayScreen()

        val animationBounce = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
        tvNews.startAnimation(animationBounce)
        tvWelcome.startAnimation(animationBounce)
    }

    override fun renderState(state: SplashState) {
        when (state) {
            is SplashState.Success -> {}
            is SplashState.Error -> {}
            is SplashState.Navigate -> navigateTo(state.navigateTo)
        }
    }
}
