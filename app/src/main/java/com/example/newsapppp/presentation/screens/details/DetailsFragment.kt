package com.example.newsapppp.presentation.screens.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.newsapppp.common.Constants.DURATION_100L
import com.example.newsapppp.common.Constants.SCALE_0_5F
import com.example.newsapppp.common.Constants.SCALE_0_83F
import com.example.newsapppp.common.Constants.SCALE_0_8F
import com.example.newsapppp.common.Constants.SCALE_1F
import com.example.newsapppp.common.Constants.SCALE_1_5F
import com.example.newsapppp.databinding.FragmentDetailsBinding
import com.example.newsapppp.common.extensions.showAlertUpDialog
import com.example.newsapppp.common.extensions.showSnackBar
import com.example.newsapppp.presentation.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment :
    BaseFragment<DetailsState, DetailsAction, FragmentDetailsBinding, DetailsViewModel>(
        FragmentDetailsBinding::inflate
    ) {
    private val articleArgs: DetailsFragmentArgs by navArgs()
    override val viewModel by viewModels<DetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setupFavoriteIcon(articleArgs.article)
        initialToolBar(binding.toolbar)
        setupWebView()
    }

    override fun onClickListener() = with(binding) {
        btFavorite.setOnClickListener {
            it.startAnimation(animationForFavoriteFlag())
            viewModel.onFavoriteButtonClicked(articleArgs.article)
        }
        btShared.setOnClickListener {
            shareMode()
        }
    }

    private fun setupWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(articleArgs.article.url)
        }
    }

    override fun observerState(state: DetailsState) {
        when (state) {
            is DetailsState.ShowFavoriteIcon -> setImageResource(state.favoriteIcon)
        }
    }

    override fun observerAction(actions: DetailsAction) {
        when (actions) {
            is DetailsAction.ShowFavoriteIcon -> {
                setImageResource(actions.favoriteIcon)
                showAlertUpDialog(actions.status)
            }
            is DetailsAction.ShowMessage -> showSnackBar(actions.message)
        }
    }

    private fun setImageResource(data: Int) = binding.btFavorite.setImageResource(data)

    private fun shareMode() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, articleArgs.article.url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun animationForFavoriteFlag(): AnimationSet {
        val toSmall = ScaleAnimation(SCALE_1F, SCALE_0_8F, SCALE_1F, SCALE_0_8F, Animation.RELATIVE_TO_SELF, SCALE_0_5F, Animation.RELATIVE_TO_SELF, SCALE_0_5F)
        val smallToLarge = ScaleAnimation(SCALE_1F, SCALE_1_5F, SCALE_1F, SCALE_1_5F, Animation.RELATIVE_TO_SELF, SCALE_0_5F, Animation.RELATIVE_TO_SELF, SCALE_0_5F)
        val largeToNormal = ScaleAnimation(SCALE_1F, SCALE_0_83F, SCALE_1F, SCALE_0_83F, Animation.RELATIVE_TO_SELF, SCALE_0_5F, Animation.RELATIVE_TO_SELF, SCALE_0_5F)
        val animationSet = AnimationSet(true).apply {
            addAnimation(toSmall)
            addAnimation(smallToLarge)
            addAnimation(largeToNormal)
        }
        animationSet.animations.forEachIndexed { index, animation ->
            animation.duration = DURATION_100L
            animation.startOffset = index * DURATION_100L
        }
        return animationSet
    }
}
