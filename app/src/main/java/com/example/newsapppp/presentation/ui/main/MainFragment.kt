package com.example.newsapppp.presentation.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapppp.R
import com.example.newsapppp.core.extensions.invisible
import com.example.newsapppp.core.extensions.navigateDirections
import com.example.newsapppp.core.extensions.navigateTo
import com.example.newsapppp.core.extensions.visible
import com.example.newsapppp.databinding.FragmentMainBinding
import com.example.newsapppp.presentation.adapters.NewsAdapter
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : BaseFragment<MainState, FragmentMainBinding, MainFragmentViewModel>(
    FragmentMainBinding::inflate
) {
    private val newsAdapter by lazy { NewsAdapter() }
    override val viewModel by viewModels<MainFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getCountryAndCategoryTabLayout()
        viewModel.getNews(category = categories[0])
        setupOnClickListeners()
        viewModel.getCountryFlag()
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(requireContext(), "Disabled Back Press", Toast.LENGTH_SHORT).show()
        }
    }

    override fun renderState(state: MainState) = with(binding) {
        when (state) {
            is MainState.ShowLoading -> {
                progressBar.visible()
            }
            is MainState.ShowArticles -> {
                newsAdapter.submitList(state.articles)
                progressBar.invisible()
                tvCenterText.invisible()
            }
            is MainState.ShowBottom -> fabUp.invisible()
            is MainState.HideBottom -> fabUp.visible()
            is MainState.GetCountryFlag -> tvCountry.text = state.getCountryFlag
        }
    }

    private fun setupOnClickListeners() {
        btProfile.setOnClickListener {
            navigateTo(R.id.settingsFragment)
        }
        newsAdapter.setOnItemClickListener {
            navigateDirections(MainFragmentDirections.actionMainFragmentToNewsFragment(it))
        }
        // Swipe to refresh
        swipeToRefresh.setOnRefreshListener {
            binding.rvNews.adapter = newsAdapter
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun getCountryAndCategoryTabLayout() {
        binding.tabMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.getNews(categories[tab.position])
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setupRecyclerView() = with(binding) {
        rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            rvNews.setHasFixedSize(true)
            toFirstRecyclerPosition()
        }
    }

    private fun getFirstNewsPosition(): Int {
        return (rvNews?.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0
    }

    private fun toFirstRecyclerPosition() {
        rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                viewModel.showOrHideFloatButton(getFirstNewsPosition())
            }
        })
        fabUp.setOnClickListener { rvNews.smoothScrollToPosition(0) }
    }

    private val categories = listOf(
        "Technology",
        "Sports",
        "Science",
        "Entertainment",
        "Business",
        "Health",
    )
}
