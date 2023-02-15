package com.example.newsapppp.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapppp.R
import com.example.newsapppp.core.SimpleTabSelectedListener
import com.example.newsapppp.databinding.FragmentMainBinding
import com.example.newsapppp.presentation.adapters.NewsPagerAdapter
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainState, FragmentMainBinding, MainFragmentViewModel>(
    FragmentMainBinding::inflate
) {

    private val newsAdapter by lazy { NewsPagerAdapter() }
    override val viewModel by viewModels<MainFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.interceptorErrors()
        getCountryAndCategoryTabLayout()
        viewModel.setupUi(categories.first())
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity()) {
            showSnackBarString(requireView(), getString(R.string.disabled_back_press))
        }
    }

    override fun onClickListener() = with(binding) {
        btSettings.setOnClickListener {
            viewModel.onBtSettingsClicked()
        }
        newsAdapter.setOnItemClickListener {
            viewModel.onNewsAdapterClicked(it)
        }
        swipeToRefresh.setOnRefreshListener {
            binding.rvNews.adapter = newsAdapter
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            rvNews.setHasFixedSize(true)
            toFirstRecyclerPosition()

            newsAdapter.addLoadStateListener { loadState ->
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                tvCenterText.isVisible = loadState.source.refresh is LoadState.Loading
                rvNews.isVisible = loadState.source.refresh is LoadState.NotLoading

                if (loadState.source.refresh is LoadState.NotLoading && newsAdapter.itemCount < 1) {
                    progressBar.invisible()
                    tvCenterText.visible()
                }
            }
        }
    }

    override fun observerState(state: MainState) = with(binding) {
        when (state) {
            is MainState.ShowLoading -> progressBar.visible()
            is MainState.SettingsClicked -> navigateTo(state.navigate)
            is MainState.AdapterClicked -> navigateDirections(state.navigate)
            is MainState.SetupUi -> {
                newsAdapter.submitData(lifecycle, state.article)
                tvCountry.text = state.countryFlag
            }
            is MainState.BottomVisibility -> fabUp.isVisible = state.state
            is MainState.Error -> showSnackBarString(requireView(), state.exception)
        }
    }

    private fun getCountryAndCategoryTabLayout() {
        binding.tabMain.addOnTabSelectedListener(object : SimpleTabSelectedListener() {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.setupUi(categories[tab.position])
            }
        })
    }

    private fun getFirstNewsPosition(): Int {
        return (binding.rvNews.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
            ?: 0
    }

    private fun toFirstRecyclerPosition() = with(binding) {
        rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                viewModel.showOrHideFloatButton(getFirstNewsPosition())
            }
        })
        fabUp.setOnClickListener { rvNews.smoothScrollToPosition(0) }
    }

    val categories = listOf(
        "Technology",
        "Sports",
        "Science",
        "Entertainment",
        "Business",
        "Health",
    )
}
