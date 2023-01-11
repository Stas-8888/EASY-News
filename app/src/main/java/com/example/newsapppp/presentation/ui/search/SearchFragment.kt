package com.example.newsapppp.presentation.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapppp.presentation.utils.extensions.navigateDirections
import com.example.newsapppp.databinding.FragmentSearchBinding
import com.example.newsapppp.presentation.adapters.NewsAdapter
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchState, FragmentSearchBinding, SearchFragmentViewModel>(
    FragmentSearchBinding::inflate
) {
    private val newsAdapter by lazy { NewsAdapter() }
    override val viewModel by viewModels<SearchFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etSearch.requestFocus()
        setupRecyclerView()
        setupOnClickListeners()
    }

    override fun renderState(state: SearchState) {
        when (state) {
            is SearchState.Loading -> {}
            is SearchState.ShowArticles -> newsAdapter.submitList(state.articles)
            is SearchState.Error -> {
                showErrorMessage(state.message)
            }
        }
    }

    private fun showErrorMessage(message: String) {
        binding.itemErrorMessage.errorCard.visibility = View.VISIBLE
        binding.itemErrorMessage.tvErrorMessage.text = message
    }

    private fun hideErrorMessage() {
        binding.itemErrorMessage.errorCard.visibility = View.GONE
    }

    private fun setupOnClickListeners() {
        newsAdapter.setOnItemClickListener {
            navigateDirections(SearchFragmentDirections.actionSearchFragmentToNewsFragment(it))
        }
        etSearch.addTextChangedListener { editable ->
            editable?.let {
                viewModel.searchTextListener(editable.toString())
            }
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
