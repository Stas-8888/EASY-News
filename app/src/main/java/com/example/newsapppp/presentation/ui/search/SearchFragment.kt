package com.example.newsapppp.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapppp.databinding.FragmentSearchBinding
import com.example.newsapppp.presentation.adapters.NewsAdapter
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchState, FragmentSearchBinding, SearchFragmentViewModel>() {
    private val newsAdapter by lazy { NewsAdapter() }
    override val viewModel by viewModels<SearchFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etSearch.requestFocus()
        setupRecyclerView()
        setupOnClickListeners()
    }

    override fun renderState(state: SearchState) {
        binding.apply {
            when (state) {
                is SearchState.ShowArticles -> {
                    newsAdapter.submitList(state.articles)
                }
            }
        }
    }

    private fun setupOnClickListeners() {
        newsAdapter.setOnItemClickListener {
            navigateDirections(SearchFragmentDirections.actionSearchFragmentToNewsFragment(it))
        }
        etSearch.addTextChangedListener { editable ->
            editable?.let {
                if (editable.toString().isNotEmpty()) {
                    viewModel.getSearchRetrofit(editable.toString())
                }
            }
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchBinding.inflate(inflater, container, false)
}
