package com.example.newsapppp.presentation.screens.main

import android.os.Bundle
import android.view.View
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
        initRecyclerView(recyclerView = binding.rvNews, baseAdapter = newsAdapter)
        onScrollRecyclerViewListener()
        viewModel.setupUi()
        adapterLoadState()
        setupTabLayout()
        setupAnimation()
    }

    override fun onClickListener() = with(binding) {
        btSettings.setOnClickListener {
            it.clickAnimation()
            viewModel.onBtSettingsClicked()
        }
        newsAdapter.setOnItemClickListener {
            viewModel.onNewsAdapterClicked(it)
        }
        swipeToRefresh.setOnRefreshListener {
            viewModel.setupUi()
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun adapterLoadState() = with(binding) {
        newsAdapter.addLoadStateListener { loadState ->
            when (val data = loadState.refresh) {
                is LoadState.Error -> {
                    progressBar.isGone()
                    showSnackBarString(data.error.message ?: "Some Error")
                }
                is LoadState.Loading -> {
                    progressBar.isVisible()
                    tvCenterText.isVisible()
                }
                is LoadState.NotLoading -> {
                    progressBar.isGone()
                    tvCenterText.isGone()
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

    override fun observerAction(actions: MainAction) {
        when (actions) {
            is MainAction.ShowMessage -> showSnackBarString(actions.message)
            is MainAction.Navigate -> navigateDirections(actions.navigateTo)
        }
    }

    private fun setupTabLayout() = with(binding) {
        // Add tabs to the TabLayout
        tabMain.apply {
            addTab(newTab().setText(R.string.main_news))
            addTab(newTab().setText(R.string.sport))
            addTab(newTab().setText(R.string.science))
            addTab(newTab().setText(R.string.entertainment))
        }

        // Set up the listener to handle tab selection events
        tabMain.addOnTabSelectedListener(object : SimpleTabSelectedListener() {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.setupTabLayout(tab)
            }
        })
    }

    private fun getFirstItemPosition(): Int {
        val layoutManager = binding.rvNews.layoutManager as? LinearLayoutManager
        return layoutManager?.findFirstVisibleItemPosition() ?: 0
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
        mainScreen.fadeInAnimation()
        tabMain.showWithAnimate(R.anim.fade_in)
        appName.translateAnimation(0f, 0f, -100f, 0f, 1000)
        tvCountry.translateAnimation(0f, 0f, 100f, 0f, 1000)
        btSettings.translateAnimation(200f, 0f, 0f, 0f, 1000)
    }
}
