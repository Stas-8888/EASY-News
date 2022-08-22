package com.example.newsapppp.presentation.fragments.main

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.app.ActivityCompat.recreate
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentMainBinding
import com.example.newsapppp.presentation.adapters.NewsAdapter
import com.example.newsapppp.presentation.fragments.base.BaseFragment
import com.example.newsapppp.presentation.fragments.SaveState
import com.example.newsapppp.presentation.utils.USA
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.no_internet_connections.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>() {
    private val newsAdapter by lazy { NewsAdapter() }
    private lateinit var sharedPref: SharedPreferences
    override val viewModel by viewModels<MainFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getCountryAndCategoryTabLayout()
        showNewsList()
        onClickListener()
    }

    private fun onClickListener() {
        tvCountry.setOnClickListener {
            rvNews.smoothScrollToPosition(0)
        }
        btProfile.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }
        newsAdapter.setOnItemClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToNewsFragment(it))
        }
    }

    private fun showNewsList() = lifecycleScope.launch {
        viewModel.state.collect {
            when (it) {
                is SaveState.ShowLoading -> {
                    binding.progressBar.isVisible = true
                }
                is SaveState.HideLoading -> {
                    noInternetConnectionDialog()
                    binding.progressBar.isVisible = true
                }
                is SaveState.ShowArticles -> {
                    binding.tvCenterText.isVisible = false
                    binding.progressBar.isVisible = false
                    newsAdapter.submitList(it.articles)
                }
                is SaveState.ShowErrorScreen -> {
                    noInternetConnectionDialog()
                }
            }
        }
    }

    private fun noInternetConnectionDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.no_internet_connections)
        dialog.setCanceledOnTouchOutside(false)

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.bt_try_again.setOnClickListener {
            recreate(requireActivity())
        }
        dialog.show()
    }

    private fun setupRecyclerView() = with(binding) {
        rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            toFirstRecyclerPosition()
        }
    }

    private fun showOrHideFloatButton() = lifecycleScope.launch {
        if (getFirstNewsPosition() < 1) {
            fabUp?.visibility = View.GONE
        } else fabUp?.visibility = View.VISIBLE
    }

    private fun getFirstNewsPosition(): Int =
        (rvNews?.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0

    private fun toFirstRecyclerPosition() {
        rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                showOrHideFloatButton()
            }
        })
        fabUp.setOnClickListener { rvNews.smoothScrollToPosition(0) }
    }

    private fun getCountryAndCategoryTabLayout() {
        sharedPref = requireActivity().getSharedPreferences("Table", Context.MODE_PRIVATE)
        val country = sharedPref.getString("country", USA)!!
        viewModel.getNewsRetrofit(countryCode = country, category = categories[0])
        tvCountry.text = country

        binding.tabMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.getNewsRetrofit(countryCode = country, categories[tab!!.position])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    private val categories = listOf(
        "Technology",
        "Sports",
        "Science",
        "Entertainment",
        "Business",
        "Health",
    )
}
