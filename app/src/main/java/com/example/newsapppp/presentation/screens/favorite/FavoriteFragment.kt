package com.example.newsapppp.presentation.screens.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapppp.databinding.FragmentFavoriteBinding
import com.example.newsapppp.presentation.adapters.NewsAdapter
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment :
    BaseFragment<FavoriteState, FavoriteAction, FragmentFavoriteBinding, FavoriteFragmentViewModel>(
        FragmentFavoriteBinding::inflate
    ) {
    private val newsAdapter by lazy { NewsAdapter() }
    override val viewModel by viewModels<FavoriteFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        swipeToDelete()
        viewModel.setupAllNews()
    }

    override fun onClickListener() {
        with(binding) {
            btDeleteAll.setOnClickListener {
                it.clickAnim()
                showDeleteDialog({ viewModel.onDeleteAllClicked() }, { })
            }
            newsAdapter.setOnItemClickListener {
                viewModel.onNewsAdapterItemClicked(it)
            }
        }
    }

    override fun observerState(state: FavoriteState) {
        with(binding) {
            when (state) {
                is FavoriteState.ShowLoading -> {
                    progressBar.visible()
                }
                is FavoriteState.ShowArticles -> {
                    newsAdapter.submitList(state.articles)
                    progressBar.isVisible = state.progressBar
                    tvBackgroundText.isVisible = state.state
                    state.exception?.let { showSnackBar(it) }
                }
            }
        }
    }

    override fun observerShared(actions: FavoriteAction) {
        when (actions) {
            is FavoriteAction.ShowDeleteDialog -> {
                showDeleteDialog(
                    { viewModel.deleteArticle(actions.article) },
                    { newsAdapter.notifyItemChanged(actions.position) })
            }
            is FavoriteAction.Navigate -> navigateDirections(actions.navigateTo)
            is FavoriteAction.ShowMessage -> showSnackBar(actions.message)
        }
    }

    private fun swipeToDelete() = with(binding) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val initial = viewHolder.bindingAdapterPosition
                val final = target.bindingAdapterPosition
                newsAdapter.notifyItemMoved(initial, final)
                return true
            }

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = 0.3f

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val article = newsAdapter.currentList[position]
                viewModel.onItemSwiped(article, position)
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }
    }

    private fun setupAdapter() = with(binding) {
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}