package com.example.newsapppp.presentation.ui.splash

import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
        viewModel.setupDayNightMode()
        viewModel.delayScreen()

        val animationBounce = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
        tvNews.startAnimation(animationBounce)
        tvWelcome.startAnimation(animationBounce)
    }

    override fun onClickListener() = with(binding) { }

    override fun renderState(state: SplashState) {
        when (state) {
            is SplashState.Success -> {}
            is SplashState.Error -> {}
            is SplashState.Navigate -> navigateTo(state.navigateTo)
        }
    }
}
