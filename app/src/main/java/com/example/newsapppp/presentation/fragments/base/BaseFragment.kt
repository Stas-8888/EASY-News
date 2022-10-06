package com.example.newsapppp.presentation.fragments.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.newsapppp.presentation.fragments.main.MainState
import kotlinx.coroutines.flow.collectLatest

abstract class BaseFragment<S: State, VB : ViewBinding, VM : BaseViewModel<S>> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected abstract val viewModel: VM
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    fun toast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun applicationContext(): Context = requireActivity().applicationContext

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeOnState()
    }

    private fun observeOnState() = lifecycleScope.launchWhenStarted {
        viewModel.state.collectLatest { state ->
            renderState(state)
        }
    }

    abstract fun renderState(state: S)

}
