package com.example.newsapppp.presentation.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.newsapppp.presentation.extensions.launchWhenStarted
import com.muddassir.connection_checker.checkConnection
import kotlinx.coroutines.flow.collectLatest

abstract class BaseFragment<State, VB : ViewBinding, VM : BaseViewModel<State>>(
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
        checkConnection(this)
        onClickListener()
        observeOnState()
    }

    private fun observeOnState() = launchWhenStarted {
        viewModel.state.collectLatest { state ->
            setObserverState(state)
        }
    }

    abstract fun onClickListener()
    abstract fun setObserverState(state: State)
}
