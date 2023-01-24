package com.example.newsapppp.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapppp.databinding.FragmentSearchBinding
import com.example.newsapppp.presentation.adapters.NewsAdapter
import com.example.newsapppp.presentation.extensions.navigateDirections
import com.example.newsapppp.presentation.extensions.showSnackBarString
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchState, FragmentSearchBinding, SearchFragmentViewModel>(
    FragmentSearchBinding::inflate
) {
    private val newsAdapter by lazy { NewsAdapter() }
    override val viewModel by viewModels<SearchFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etSearch.requestFocus()
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onClickListener() = with(binding) {
        etSearch.addTextChangedListener { editable ->
            editable?.let {
                viewModel.searchTextListener(editable.toString())
            }
        }
        newsAdapter.setOnItemClickListener {
            navigateDirections(SearchFragmentDirections.actionSearchFragmentToNewsFragment(it))
        }
    }

    override fun renderState(state: SearchState) {
        when (state) {
            is SearchState.Loading -> {}
            is SearchState.ShowArticles -> newsAdapter.submitList(state.articles)
            is SearchState.Error -> showSnackBarString(requireView(), state.message)
        }
    }
}
