package com.example.newsapppp.presentation.screens.news

import android.content.Intent
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

const val SHARED_TEXT = "Check this news."
const val TYPE = "text/plain"

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
            viewModel.onFavoriteButtonClicked(articleArgs.article)
        }
        btShared.setOnClickListener {
            shareMode()
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

    private fun shareMode() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, SHARED_TEXT)
            type = TYPE
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
