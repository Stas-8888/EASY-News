package com.example.newsapppp.presentation.ui.splash

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentSplashBinding
import com.example.newsapppp.presentation.extensions.navigateDirections
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment :
    BaseFragment<SplashState, SplashAction, FragmentSplashBinding, SplashViewModel>(
        FragmentSplashBinding::inflate
    ) {
    override val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        val animationBounce = AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)
        tvNews.startAnimation(animationBounce)
        tvWelcome.startAnimation(animationBounce)
    }

    override fun onClickListener() {
        viewModel.setupDayNightMode()
        viewModel.navigateToLoginFragment()
    }

    override fun observerState(state: SplashState) {}
    override fun observerShared(actions: SplashAction) {
        when (actions) {
            is SplashAction.Navigate -> navigateDirections(actions.navigateTo)
        }
    }
}
