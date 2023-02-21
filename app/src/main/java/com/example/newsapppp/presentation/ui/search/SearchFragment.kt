package com.example.newsapppp.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapppp.databinding.FragmentSearchBinding
import com.example.newsapppp.presentation.adapters.NewsAdapter
import com.example.newsapppp.presentation.extensions.internetConnectionDialog
import com.example.newsapppp.presentation.extensions.navigateDirections
import com.example.newsapppp.presentation.extensions.showSnackBar
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment :
    BaseFragment<SearchState, SearchAction, FragmentSearchBinding, SearchFragmentViewModel>(
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
            viewModel.onItemClicked(it)
        }
    }

    override fun observerState(state: SearchState) {
        when (state) {
            is SearchState.Loading -> {}
            is SearchState.ShowArticles -> newsAdapter.submitList(state.articles)
        }
    }

    override fun observerShared(actions: SearchAction) {
        when (actions) {
            is SearchAction.ShowMessage -> showSnackBar(actions.message)
            is SearchAction.Navigate -> navigateDirections(actions.navigateTo)
            is SearchAction.ShowNetworkConnections -> internetConnectionDialog(getString(actions.message))
        }
    }
}
