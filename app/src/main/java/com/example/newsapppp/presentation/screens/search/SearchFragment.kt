package com.example.newsapppp.presentation.screens.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.newsapppp.presentation.extensions.navigateDirections
import com.example.newsapppp.presentation.extensions.showInternetConnectionDialog
import com.example.newsapppp.presentation.extensions.showKeyboard
import com.example.newsapppp.presentation.extensions.showSnackBar
import com.example.newsapppp.databinding.FragmentSearchBinding
import com.example.newsapppp.presentation.adapters.ArticleAdapter
import com.example.newsapppp.presentation.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment :
    BaseFragment<SearchState, SearchAction, FragmentSearchBinding, SearchViewModel>(
        FragmentSearchBinding::inflate
    ) {
    private val articleAdapter by lazy { ArticleAdapter() }
    override val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        etSearchQuery.requestFocus()
        setupRecyclerView(rvSearchNews, articleAdapter)
    }

    override fun onClickListener() = with(binding) {
        etSearchQuery.addTextChangedListener { editable ->
            viewModel.searchQueryListener(editable.toString())
        }
        articleAdapter.setOnItemClickListener {
            viewModel.onItemAdapterClicked(it)
        }
    }

    override fun observerState(state: SearchState) = with(binding) {
        when (state) {
            is SearchState.Loading -> etSearchQuery.showKeyboard()
            is SearchState.ShowArticles -> articleAdapter.submitList(state.articles)
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
