package com.example.newsapppp.presentation.ui.news

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentNewsBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.example.newsapppp.presentation.utils.extensions.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.delete_dialog3.view.*
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkFavoriteIcon(article)
        setupWebView()
        setupOnClickListeners()
        hideBottomNavigation()
    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun setupWebView() {
        webView.apply {
            webView.webViewClient = WebViewClient()
            loadUrl(article.url)
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
    }

    private fun setupOnClickListeners() = with(binding) {
        btFavorite.setOnClickListener {
            binding.btFavorite.invisible()
            binding.circleAnimeView.visible()
            binding.circleAnimeView.startAnim(
                anim,
                onEnd = {
                    binding.circleAnimeView.visible()
                    viewModel.onFavoriteIconClicked(article)
                    binding.btFavorite.visible()
                }
            )
        }
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
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
