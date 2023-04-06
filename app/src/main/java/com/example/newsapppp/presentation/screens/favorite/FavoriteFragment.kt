package com.example.newsapppp.presentation.screens.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.newsapppp.databinding.FragmentFavoriteBinding
import com.example.newsapppp.presentation.adapters.ArticleAdapter
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment :
    BaseFragment<FavoriteState, FavoriteAction, FragmentFavoriteBinding, FavoriteFragmentViewModel>(
        FragmentFavoriteBinding::inflate
    ) {
    private val articleAdapter by lazy { ArticleAdapter() }
    override val viewModel by viewModels<FavoriteFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(binding.rvSavedNews, articleAdapter)
        viewModel.setupAllArticles()
        initItemTouchHelper()
    }

    private fun initItemTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(SwipeToDelete(articleAdapter, viewModel))
        itemTouchHelper.attachToRecyclerView(binding.rvSavedNews)
    }

    override fun onClickListener() = with(binding) {
        btDeleteAll.setOnClickListener {
            it.clickAnimation()
            viewModel.onDeleteAllClicked()
        }
        articleAdapter.setOnItemClickListener {
            viewModel.onNewsAdapterItemClicked(it)
        }
    }

    override fun observerState(state: FavoriteState): Unit = with(binding) {
        when (state) {
            is FavoriteState.Loading -> progressBar.isVisible()
            is FavoriteState.ShowArticles -> {
                articleAdapter.submitList(state.articles)
                progressBar.isVisible = state.progressBar
                tvBackgroundText.isVisible = state.state
                state.exception?.let { showSnackBar(it) }
            }
        }
    }

    override fun observerAction(actions: FavoriteAction) {
        when (actions) {
            is FavoriteAction.Navigate -> navigateDirections(actions.navigateTo)
            is FavoriteAction.ShowMessage -> showSnackBar(actions.message)
            is FavoriteAction.ShowDeleteAllDialog -> showDeleteDialog(
                { viewModel.onSuccessDeleteDialogAction() }, { })
            is FavoriteAction.ShowItemDeleteDialog -> showDeleteDialog(
                { viewModel.deleteArticle(actions.article) },
                { articleAdapter.notifyItemChanged(actions.position) })
        }
    }
}
