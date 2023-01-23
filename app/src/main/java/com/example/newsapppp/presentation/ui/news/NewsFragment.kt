package com.example.newsapppp.presentation.ui.news

import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentNewsBinding
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news.*

@AndroidEntryPoint
class NewsFragment : BaseFragment<NewsState, FragmentNewsBinding, NewsFragmentViewModel>(
    FragmentNewsBinding::inflate
) {
    private val args: NewsFragmentArgs by navArgs()
    override val viewModel by viewModels<NewsFragmentViewModel>()
    private val article by lazy { args.article }
    private val anim by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.fab_explode).apply {
            interpolator = AccelerateDecelerateInterpolator()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkFavoriteIcon(article)
        setupWebView()
        hideBottomNavigation()
    }

    override fun setupUi() = with(binding) {
        btFavorite.setOnClickListener {
            btFavorite.invisible()
            circleAnimeView.visible()
            circleAnimeView.startAnim(
                anim,
                onEnd = {
                    circleAnimeView.visible()
                    viewModel.onFavoriteIconClicked(article)
                    btFavorite.visible()
                }
            )
        }
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupWebView() {
        webView.apply {
            webView.webViewClient = WebViewClient()
            loadUrl(article.url)
        }
    }

    override fun renderState(state: NewsState) = with(binding) {
        when (state) {
            is NewsState.DeleteFavoriteArticle -> {
                setImageResource(state.favoriteIcon)
                showAlertUpDialog(state.status)
            }
            is NewsState.SaveFavoriteArticle -> {
                setImageResource(state.favoriteIcon)
                showAlertUpDialog(state.status)
            }
            is NewsState.Error -> {
                btFavorite.invisible()
                snackBar(btFavorite, state.message)
            }
            is NewsState.HideFavoriteIcon -> setImageResource(state.favoriteIcon)
            is NewsState.ShowFavoriteIcon -> setImageResource(state.favoriteIcon)
        }
    }

    private fun setImageResource(data: Int) {
        binding.btFavorite.setImageResource(data)
    }
}
