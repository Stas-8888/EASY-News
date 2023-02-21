package com.example.newsapppp.presentation.screens.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.newsapppp.presentation.extensions.launchWhenStarted
import kotlinx.coroutines.flow.collectLatest

/**
 * Base class for all Fragments.
 *
 */

abstract class BaseFragment<State, Action, VB : ViewBinding, VM : BaseViewModel<State, Action>>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickListener()
        observeOnState()
        observeOnShared()
    }

    private fun observeOnState() = launchWhenStarted {
        viewModel.state.collectLatest { state ->
            observerState(state)
        }
    }

    private fun observeOnShared() = launchWhenStarted {
        viewModel.shared.collectLatest { actions ->
            observerShared(actions)
        }
    }

    abstract fun onClickListener()
    abstract fun observerState(state: State)
    abstract fun observerShared(actions: Action)
}
