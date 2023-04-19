package com.example.newsapppp.presentation.screens.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapppp.R
import com.example.newsapppp.core.Constants.DURATION_100L
import com.example.newsapppp.core.Constants.SCALE_0F
import com.example.newsapppp.core.Constants.SCALE_100F
import com.example.newsapppp.core.Constants.SCALE_200F
import com.example.newsapppp.core.Constants.SCALE_MINUS_100F
import com.example.newsapppp.core.Constants.ZERO
import com.example.newsapppp.core.SimpleTabSelectedListener
import com.example.newsapppp.databinding.FragmentMainBinding
import com.example.newsapppp.presentation.adapters.ArticlePagerAdapter
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.screens.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment :
    BaseFragment<MainState, MainAction, FragmentMainBinding, MainFragmentViewModel>(
        FragmentMainBinding::inflate
    ) {

    private val articleAdapter by lazy { ArticlePagerAdapter() }
    override val viewModel by viewModels<MainFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(recyclerView = rvNews, baseAdapter = articleAdapter)
        viewModel.showInterceptorErrors()
        viewModel.fetchAndShowArticles()
        onScrollRecyclerViewListener()
        adapterLoadState()
        setupTabLayout()
        setupAnimation()
    }

    override fun onClickListener() = with(binding) {
        btSettings.setOnClickListener {
            it.clickAnimation()
            viewModel.onBtSettingsClicked()
        }
        articleAdapter.setOnItemClickListener {
            viewModel.onNewsAdapterClicked(it)
        }
        swipeToRefresh.setOnRefreshListener {
            viewModel.fetchAndShowArticles()
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun adapterLoadState() = with(binding) {
        articleAdapter.addLoadStateListener { loadState ->
            when (val data = loadState.refresh) {
                is LoadState.Error -> {
                    progressBar.isGone()
//                    showSnackBar(R.string.error)
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
            is MainState.ShowUI -> {
                articleAdapter.submitData(lifecycle, state.article)
                tvCountry.text = state.countryFlag
            }
            is MainState.BottomVisibility -> fabUp.isVisible = state.state
        }
    }

    override fun observerAction(actions: MainAction) {
        when (actions) {
            is MainAction.ShowMessage -> showSnackBar(actions.message)
            is MainAction.Navigate -> navigateDirections(actions.navigateTo)
            is MainAction.ShowNetworkDialog -> showInternetConnectionDialog(getString(actions.message))
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
                viewModel.fetchAndShowArticles(tab)
            }
        })
    }

    private fun getFirstItemPosition(): Int {
        val layoutManager = binding.rvNews.layoutManager as? LinearLayoutManager
        return layoutManager?.findFirstVisibleItemPosition() ?: ZERO
    }

    private fun onScrollRecyclerViewListener() = with(binding) {
        rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                viewModel.showOrHideFloatButton(getFirstItemPosition())
            }
        })
        fabUp.setOnClickListener { rvNews.smoothScrollToPosition(ZERO) }
    }

    private fun setupAnimation() = with(binding) {
        mainScreen.fadeInAnimation()
        tabMain.showWithAnimate(R.anim.fade_in)
        appName.translateAnimation(SCALE_0F, SCALE_0F, SCALE_MINUS_100F, SCALE_0F, DURATION_100L)
        tvCountry.translateAnimation(SCALE_0F, SCALE_0F, SCALE_100F, SCALE_0F, DURATION_100L)
        btSettings.translateAnimation(SCALE_200F, SCALE_0F, SCALE_0F, SCALE_0F, DURATION_100L)
    }
}
