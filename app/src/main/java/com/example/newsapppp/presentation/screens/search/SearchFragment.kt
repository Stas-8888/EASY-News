package com.example.newsapppp.presentation.screens.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.newsapppp.databinding.FragmentSearchBinding
import com.example.newsapppp.presentation.adapters.NewsAdapter
import com.example.newsapppp.presentation.extensions.navigateDirections
import com.example.newsapppp.presentation.extensions.showInternetConnectionDialog
import com.example.newsapppp.presentation.extensions.showSnackBar
import com.example.newsapppp.presentation.screens.base.BaseFragment
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
        setupRecyclerView(binding.rvSearchNews, newsAdapter)
        binding.etSearchQuery.requestFocus()
    }

    override fun onClickListener() = with(binding) {
        etSearchQuery.addTextChangedListener { editable ->
            editable?.let {
                viewModel.searchQueryListener(editable.toString())
            }
        }
        newsAdapter.setOnItemClickListener {
            viewModel.onItemAdapterClicked(it)
        }
    }

    override fun observerState(state: SearchState) {
        when (state) {
            is SearchState.Loading -> {}
            is SearchState.ShowArticles -> newsAdapter.submitList(state.articles)
        }
    }

    override fun observerAction(actions: SearchAction) {
        when (actions) {
            is SearchAction.ShowMessage -> showSnackBar(actions.message)
            is SearchAction.Navigate -> navigateDirections(actions.navigateTo)
            is SearchAction.ShowNetworkDialog -> showInternetConnectionDialog(getString(actions.message))
        }
    }
}
