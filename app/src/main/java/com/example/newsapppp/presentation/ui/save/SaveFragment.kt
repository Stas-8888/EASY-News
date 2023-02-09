package com.example.newsapppp.presentation.ui.save

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapppp.databinding.FragmentSaveBinding
import com.example.newsapppp.presentation.adapters.NewsAdapter
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveFragment : BaseFragment<SaveState, FragmentSaveBinding, SaveFragmentViewModel>(
    FragmentSaveBinding::inflate
) {
    private val newsAdapter by lazy { NewsAdapter() }
    override val viewModel by viewModels<SaveFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        swipeToDelete()
        showBottomNavigation()
        viewModel.setupAllNews()
    }

    override fun onClickListener() {
        with(binding) {
            btDeleteAll.setOnClickListener {
                showDeleteDialog({ viewModel.onDeleteAllArticleClicked() }, { })
            }
            newsAdapter.setOnItemClickListener {
                navigateDirections(SaveFragmentDirections.actionSaveFragmentToNewsFragment(it))
            }
        }
    }

    override fun observerState(state: SaveState) {
        with(binding) {
            when (state) {
                is SaveState.ShowLoading -> {
                    progressBar.visible()
                }
                is SaveState.ShowArticles -> {
                    newsAdapter.submitList(state.articles)
                    progressBar.isVisible = state.progressBar
                    tvBackgroundText.isVisible = state.state
                    state.exception?.let { snackBar(requireView(), it) }
                }
                is SaveState.ShowDeleteDialog -> {
                    showDeleteDialog(
                        { viewModel.deleteArticle(state.article) },
                        { newsAdapter.notifyItemChanged(state.position) })
                }
            }
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
