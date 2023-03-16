package com.example.newsapppp.presentation.screens.news

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.newsapppp.databinding.FragmentNewsBinding
import com.example.newsapppp.presentation.extensions.clickAnimation
import com.example.newsapppp.presentation.extensions.showAlertUpDialog
import com.example.newsapppp.presentation.extensions.showSnackBar
import com.example.newsapppp.presentation.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment :
    BaseFragment<NewsState, NewsAction, FragmentNewsBinding, NewsFragmentViewModel>(
        FragmentNewsBinding::inflate
    ) {
    private val articleArgs: NewsFragmentArgs by navArgs()
    override val viewModel by viewModels<NewsFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setupFavoriteIcon(articleArgs.article)
        initialToolBar(binding.toolbar)
        setupWebView()
    }

    override fun onClickListener() = with(binding) {
        btFavorite.setOnClickListener {
            it.clickAnimation()
            viewModel.onFavoriteIconClicked(articleArgs.article)
        }
    }

    private fun setupWebView() {
        binding.webView.apply {
            binding.webView.webViewClient = WebViewClient()
            loadUrl(articleArgs.article.url)
        }
    }

    override fun observerState(state: NewsState) {
        when (state) {
            is NewsState.ShowFavoriteIcon -> setImageResource(state.favoriteIcon)
        }
    }

    override fun observerAction(actions: NewsAction) {
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
