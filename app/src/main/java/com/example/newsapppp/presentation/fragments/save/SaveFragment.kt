package com.example.newsapppp.presentation.fragments.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentSaveBinding
import com.example.newsapppp.presentation.adapters.NewsAdapter
import com.example.newsapppp.presentation.extensions.showDeleteDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_save.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SaveFragment : Fragment() {
    private lateinit var binding: FragmentSaveBinding
    private val newsAdapter by lazy { NewsAdapter() }
    private val viewModel by viewModels<SaveFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaveBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        swipeToDelete()
        showSaveList()
        viewModel.getAllNews()

        btDeleteAll.setOnClickListener {
            showDeleteDialog({ viewModel.deleteAll() }, { })
        }
        newsAdapter.setOnItemClickListener {
            findNavController().navigate(SaveFragmentDirections.actionSaveFragmentToNewsFragment(it))
        }
    }

    private fun showSaveList() = lifecycleScope.launch {
        viewModel.state.collect {
            when (it) {
                is SaveState.ShowLoading -> {
                    binding.progressBar.isVisible = true
                }
                is SaveState.ShowArticles -> {
                    binding.tvBackgroundText.isVisible = false
                    binding.progressBar.isVisible = false
                    newsAdapter.submitList(it.articles)
                }
                else -> {
                    Toast.makeText(requireContext(),"Mistake = $it",Toast.LENGTH_LONG).show()}
            }
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun swipeToDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = 0.3f

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.currentList[position]
                showDeleteDialog(
                    { viewModel.delete(article) },
                    { newsAdapter.notifyItemChanged(position) })
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }
    }
}
