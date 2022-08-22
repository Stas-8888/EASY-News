package com.example.newsapppp.presentation.fragments.news

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentNewsBinding
import com.example.newsapppp.presentation.extensions.showAlertUpDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news.*

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private val args: NewsFragmentArgs by navArgs()
    private val viewModel by viewModels<NewsFragmentViewModel>()
    var isFavorite = false
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

    private fun checkFavoriteIcon() {
        isFavorite = viewModel.getFavorite()
        if (isFavorite) {
            binding.btFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.btFavorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun saveDeleteFavorite() {
        if (isFavorite) {
            viewModel.saveFavorite(false)
            binding.btFavorite.setImageResource(R.drawable.ic_favorite_border)
            viewModel.delete(article)
            showAlertUpDialog(getString(R.string.СтатьяУдалена))
        } else {
            binding.btFavorite.setImageResource(R.drawable.ic_favorite)
            viewModel.saveFavorite(true)
            viewModel.insert(article)
            showAlertUpDialog(getString(R.string.СтатьяДобавлена))
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
