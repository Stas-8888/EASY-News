package com.example.newsapppp.presentation.ui.news

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentNewsBinding
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.example.newsapppp.presentation.utils.extensions.disable
import com.example.newsapppp.presentation.utils.extensions.invisible
import com.example.newsapppp.presentation.utils.extensions.launchWhenStarted
import com.example.newsapppp.presentation.utils.extensions.showAlertUpDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news.*

@AndroidEntryPoint
class NewsFragment : BaseFragment<NewsState, FragmentNewsBinding, NewsFragmentViewModel>(
    FragmentNewsBinding::inflate
) {
    private val args: NewsFragmentArgs by navArgs()
    override val viewModel by viewModels<NewsFragmentViewModel>()
    private val article by lazy { args.article }
//    private lateinit var firebaseAuth: FirebaseAuth


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        firebaseAuth = FirebaseAuth.getInstance()
        setupWebView()
        setupOnClickListeners()
    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
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
            saveDeleteFavorite()
        }
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun renderState(state: NewsState) {
        viewModel.checkFavoriteIcon(article)
        when (state) {
            is NewsState.ShowUnSaved -> setImageResource(R.drawable.ic_favorite)
            is NewsState.ShowAsSaved -> setImageResource(R.drawable.ic_favorite_border)
            is NewsState.Error -> setImageResource(R.drawable.ic_favorite)
        }
    }

    private fun saveDeleteFavorite() = launchWhenStarted {
        viewModel.saveDeleteFavorite(article)
        viewModel.state.collect() {
            when (it) {
                is NewsState.ShowAsSaved -> {
                    setImageResource(R.drawable.ic_favorite)
                    showAlertUpDialog(getString(R.string.СтатьяДобавлена))
                }
                is NewsState.ShowUnSaved -> {
                    setImageResource(R.drawable.ic_favorite_border)
                    showAlertUpDialog(getString(R.string.СтатьяУдалена))
                }
                is NewsState.Error -> {
                    binding.btFavorite.invisible()
                    toast("If you want to use this functions, please registered")

                }
            }
        }
    }

    private fun setImageResource(data: Int) {
        binding.btFavorite.setImageResource(data)
    }

//    override fun onStart() {
//        super.onStart()
//        binding.btFavorite.isEnabled = firebaseAuth.currentUser != null
//    }
}
