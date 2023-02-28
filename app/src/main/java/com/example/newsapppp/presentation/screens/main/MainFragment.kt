package com.example.newsapppp.presentation.screens.main

import android.os.Bundle
import android.view.View
import android.view.animation.TranslateAnimation
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
import com.example.newsapppp.presentation.screens.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment :
    BaseFragment<MainState, MainAction, FragmentMainBinding, MainFragmentViewModel>(
        FragmentMainBinding::inflate
    ) {

    private val newsAdapter by lazy { NewsPagerAdapter() }
    override val viewModel by viewModels<MainFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.interceptorErrors()
        setupRecyclerView()
        viewModel.setupUi()
        setupTabLayout()
        setupAnimation()
    }

    override fun onClickListener() = with(binding) {
        btSettings.setOnClickListener {
            it.clickAnim()
            viewModel.onBtSettingsClicked()
        }
        newsAdapter.setOnItemClickListener {
            mainScreen.fadeOut()
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
            onScrollRecyclerViewListener()
            adapterLoadState()
        }
    }

    private fun adapterLoadState() = with(binding) {
        newsAdapter.addLoadStateListener { loadState ->
            when (val data = loadState.refresh) {
                is LoadState.Error -> {
                    progressBar.invisible()
                    showSnackBarString(data.error.message ?: "Some Error")
                }
                is LoadState.Loading -> {
                    progressBar.visible()
                    tvCenterText.visible()
                }
                is LoadState.NotLoading -> {
                    progressBar.invisible()
                    tvCenterText.invisible()
                }
            }
        }
    }

    override fun observerState(state: MainState) = with(binding) {
        when (state) {
            is MainState.ShowLoading -> {}
            is MainState.ShowUI -> {
                newsAdapter.submitData(lifecycle, state.article)
                tvCountry.text = state.countryFlag
            }
            is MainState.BottomVisibility -> fabUp.isVisible = state.state
        }
    }

    override fun observerShared(actions: MainAction) {
        when (actions) {
            is MainAction.ShowMessage -> showSnackBarString(actions.message)
            is MainAction.Navigate -> navigateDirections(actions.navigateTo)
        }
    }

    private fun setupTabLayout() = with(binding) {
        tabMain.addTab(tabMain.newTab().setText((R.string.main_news)))
        tabMain.addTab(tabMain.newTab().setText((R.string.sport)))
        tabMain.addTab(tabMain.newTab().setText((R.string.science)))
        tabMain.addTab(tabMain.newTab().setText((R.string.entertainment)))
        tabMain.addOnTabSelectedListener(object : SimpleTabSelectedListener() {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.setupTabLayout(tab)
            }
        })
    }

    private fun getFirstItemPosition(): Int {
        return (binding.rvNews.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
            ?: 0
    }

    private fun onScrollRecyclerViewListener() = with(binding) {
        rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                viewModel.showOrHideFloatButton(getFirstItemPosition())
            }
        })
        fabUp.setOnClickListener { rvNews.smoothScrollToPosition(0) }
    }

    private fun setupAnimation() = with(binding) {
        mainScreen.fadeIn()
        appName.startAnimation(TranslateAnimation(0f, 0f, -100f, 0f).apply {
            duration = 1000
        })
        tvCountry.startAnimation(TranslateAnimation(0f, 0f, 100f, 0f).apply {
            duration = 1000
        })
        btSettings.startAnimation(TranslateAnimation(200f, 0f, 0f, 0f).apply {
            duration = 1000
        })
    }
}
