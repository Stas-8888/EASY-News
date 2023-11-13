package com.example.newsapppp.presentation.screens.main

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapppp.R
import com.example.newsapppp.common.Constants.DURATION_100L
import com.example.newsapppp.common.Constants.SCALE_0F
import com.example.newsapppp.common.Constants.SCALE_100F
import com.example.newsapppp.common.Constants.SCALE_200F
import com.example.newsapppp.common.Constants.SCALE_MINUS_100F
import com.example.newsapppp.common.Constants.ZERO
import com.example.newsapppp.common.SimpleTabSelectedListener
import com.example.newsapppp.databinding.FragmentMainBinding
import com.example.newsapppp.presentation.extensions.fadeInAnimation
import com.example.newsapppp.presentation.extensions.makeGone
import com.example.newsapppp.presentation.extensions.makeVisible
import com.example.newsapppp.presentation.extensions.navigateDirections
import com.example.newsapppp.presentation.extensions.showInternetConnectionDialog
import com.example.newsapppp.presentation.extensions.showSnackBar
import com.example.newsapppp.presentation.extensions.showWithAnimate
import com.example.newsapppp.presentation.extensions.translateAnimation
import com.example.newsapppp.presentation.model.Article
import com.example.newsapppp.presentation.screens.base.BaseFragment
import com.example.newsapppp.presentation.screens.main.adapter.ArticlePagingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainState, MainAction, FragmentMainBinding, MainViewModel>(
    FragmentMainBinding::inflate
) {
    private val adapter: ArticlePagingAdapter by lazy { ArticlePagingAdapter(::onArticleItemClicked) }
    override val viewModel by viewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(recyclerView = rvNews, baseAdapter = adapter)
        showBottomNavigationViewOnDrawerClosed(mainScreen)
        viewModel.showInterceptorErrors()
        onScrollRecyclerViewListener()
        viewModel.showArticles()
        adapterLoadState()
        setupTabLayout()
        setupAnimation()
    }

    private fun showBottomNavigationViewOnDrawerClosed(drawerLayout: DrawerLayout) {
        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                hideBottomNavigationView(true)
            }
        })
    }

    private fun hideBottomNavigationView(visibility: Boolean) {
        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.isVisible = visibility
    }

    private fun onArticleItemClicked(article: Article){
        viewModel.onArticleItemClicked(article)
    }

    override fun onClickListener() = with(binding) {
        btSettings.setOnClickListener {
            viewModel.onBtSettingsClicked()
        }

        swipeToRefresh.setOnRefreshListener {
            viewModel.showArticles()
            swipeToRefresh.isRefreshing = false
        }
        imMenu.setOnClickListener {
            mainScreen.openDrawer(GravityCompat.START)
            hideBottomNavigationView(false)
        }
    }

    private fun adapterLoadState() = with(binding) {
        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Error -> {
                    progressBar.makeGone()
                }

                is LoadState.Loading -> {
                    progressBar.makeVisible()
                    tvCenterText.makeVisible()
                }

                is LoadState.NotLoading -> {
                    progressBar.makeGone()
                    tvCenterText.makeGone()
                }
            }
        }
    }

    override fun observerState(state: MainState) = with(binding) {
        when (state) {
            is MainState.ShowUI -> {
                adapter.submitData(viewLifecycleOwner.lifecycle, state.article)
                tvCountry.text = state.countryFlag
            }

            is MainState.BottomVisibility -> fabUp.isVisible = state.state
        }
    }

    override fun observerAction(actions: MainAction) {
        when (actions) {
            is MainAction.ShowError -> showSnackBar(actions.message)
            is MainAction.OnClicked -> navigateDirections(actions.navigateTo)
            is MainAction.ShowNetworkDialog -> showInternetConnectionDialog(getString(actions.message))
        }
    }

    private fun setupTabLayout() = with(binding) {
        tabMain.addOnTabSelectedListener(object : SimpleTabSelectedListener() {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewModel.showArticles(tab.text.toString())
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

    override fun onDestroyView() {
        binding.rvNews.adapter = null
        super.onDestroyView()
    }
}
