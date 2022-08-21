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
import com.example.newsapppp.presentation.utils.SaveShared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news.*

@AndroidEntryPoint
class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private val args: NewsFragmentArgs by navArgs()
    private val viewModel by viewModels<NewsFragmentViewModel>()
    var isFavorite = false

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
        initi()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initi() {
        val article = args.article
        webView.apply {
            webView.webViewClient = WebViewClient()
            loadUrl(article.url!!)
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }

        isFavorite = SaveShared.getFavorite(requireContext(), article.id.toString())
        if (isFavorite) {
            binding.btFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.btFavorite.setImageResource(R.drawable.ic_favorite_border)
        }

        binding.btFavorite.setOnClickListener {
            if (isFavorite) {
                SaveShared.setFavorite(requireContext(), article.id.toString(), false)
                binding.btFavorite.setImageResource(R.drawable.ic_favorite_border)
                viewModel.delete(article)
                showAlertUpDialog(getString(R.string.СтатьяУдалена))
                isFavorite = false
            } else {
                binding.btFavorite.setImageResource(R.drawable.ic_favorite)
                SaveShared.setFavorite(requireContext(), article.id.toString(), true)
                viewModel.insert(article)
                showAlertUpDialog(getString(R.string.СтатьяДобавлена))
                isFavorite = true
            }
        }
    }
}
