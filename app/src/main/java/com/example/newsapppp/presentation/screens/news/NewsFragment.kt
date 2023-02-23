package com.example.newsapppp.presentation.screens.news

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentNewsBinding
import com.example.newsapppp.presentation.extensions.showAlertUpDialog
import com.example.newsapppp.presentation.extensions.showSnackBar
import com.example.newsapppp.presentation.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment :
    BaseFragment<NewsState, NewsAction, FragmentNewsBinding, NewsFragmentViewModel>(
        FragmentNewsBinding::inflate
    ) {
    private val args: NewsFragmentArgs by navArgs()
    override val viewModel by viewModels<NewsFragmentViewModel>()
    private val article by lazy { args.article }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setupFavoriteIcon(article)
        setupWebView()
    }

    override fun onClickListener() = with(binding) {
        btFavorite.setOnClickListener {
            val animAlpha = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_explode)
            btFavorite.startAnimation(animAlpha)
            viewModel.onFavoriteIconClicked(article)
        }
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupWebView() {
        binding.webView.apply {
            binding.webView.webViewClient = WebViewClient()
            loadUrl(article.url)
        }
    }

    override fun observerState(state: NewsState) {
        when (state) {
            is NewsState.ShowFavoriteIcon -> setImageResource(state.favoriteIcon)
        }
    }

    override fun observerShared(actions: NewsAction) {
        when (actions) {
            is NewsAction.ShowFavoriteIcon -> {
                setImageResource(actions.favoriteIcon)
                showAlertUpDialog(actions.status)
            }
            is NewsAction.ShowMessage -> showSnackBar(actions.message)
        }
    }

    private fun setImageResource(data: Int) = binding.btFavorite.setImageResource(data)
}
