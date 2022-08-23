package com.example.newsapppp.presentation.fragments.news

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentNewsBinding
import com.example.newsapppp.presentation.extensions.showAlertUpDialog
import com.example.newsapppp.presentation.fragments.SaveState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private val args: NewsFragmentArgs by navArgs()
    private val viewModel by viewModels<NewsFragmentViewModel>()
    val article by lazy { args.article }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showWebView()
        viewModel.checkFavoriteIcon()
        checkFavoriteIcon()
        onClick()
    }

    private fun onClick() {
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
                is SaveState.ShowAsSavedFalse -> {
                    binding.btFavorite.setImageResource(R.drawable.ic_favorite)
                }
                is SaveState.ShowAsSavedTrue -> {
                    binding.btFavorite.setImageResource(R.drawable.ic_favorite_border)
                }
            }
        }
    }

    private fun saveDeleteFavorite() = lifecycleScope.launch {
        viewModel.saveDeleteFavorite(article)
        viewModel.state.collect() {
            when (it) {
                is SaveState.ShowDelete -> {
                    binding.btFavorite.setImageResource(R.drawable.ic_favorite_border)
                    showAlertUpDialog(getString(R.string.СтатьяУдалена))
                }
                is SaveState.ShowInsert -> {
                    binding.btFavorite.setImageResource(R.drawable.ic_favorite)
                    showAlertUpDialog(getString(R.string.СтатьяДобавлена))
                }
            }
        }
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
}
