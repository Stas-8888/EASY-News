package com.example.newsapppp.presentation.fragments.news

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentNewsBinding
import com.example.newsapppp.presentation.extensions.showAlertUpDialog
import com.example.newsapppp.presentation.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding, NewsFragmentViewModel>() {
    private val args: NewsFragmentArgs by navArgs()
    override val viewModel by viewModels<NewsFragmentViewModel>()
    val article by lazy { args.article }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showWebView()
        viewModel.checkFavoriteIcon()
        checkFavoriteIcon()
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        binding.btFavorite.setOnClickListener {
            saveDeleteFavorite()
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun checkFavoriteIcon() = lifecycleScope.launch {
        viewModel.state.collect() {
            when (it) {
                is NewsState.ShowUnSaved -> {
                    setImageResource(R.drawable.ic_favorite)
                }
                is NewsState.ShowAsSaved -> {
                    setImageResource(R.drawable.ic_favorite_border)
                }
            }
        }
    }

    private fun saveDeleteFavorite() = lifecycleScope.launch {
        viewModel.saveDeleteFavorite(article)
        viewModel.state.collect() {
            when (it) {
                is NewsState.ShowDelete -> {
                    setImageResource(R.drawable.ic_favorite_border)
                    showAlertUpDialog(getString(R.string.СтатьяУдалена))
                }
                is NewsState.ShowInsert -> {
                    setImageResource(R.drawable.ic_favorite)
                    showAlertUpDialog(getString(R.string.СтатьяДобавлена))
                }
            }
        }
    }

    private fun setImageResource(data: Int){
        binding.btFavorite.setImageResource(data)
    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showWebView() {
        webView.apply {
            webView.webViewClient = WebViewClient()
            loadUrl(article.url!!)
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNewsBinding.inflate(inflater, container, false)
}
