package com.example.newsapppp.presentation.screens.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.newsapppp.presentation.extensions.bumpAnimation
import com.example.newsapppp.presentation.extensions.launchWhenStarted
import com.example.newsapppp.presentation.extensions.returnToPreviousScreen
import kotlinx.coroutines.flow.collectLatest

/**
 * Base class for all Fragments.
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
        observeOnAction()
    }

    private fun observeOnState() = launchWhenStarted {
        viewModel.state.collectLatest { state ->
            observerState(state)
        }
    }

    private fun observeOnAction() = launchWhenStarted {
        viewModel.action.collectLatest { actions ->
            observerAction(actions)
        }
    }

    abstract fun onClickListener()
    abstract fun observerState(state: State)
    abstract fun observerAction(actions: Action)

    protected fun initialToolBar(toolbar: androidx.appcompat.widget.Toolbar) {
        toolbar.startAnimation(TranslateAnimation(1000f, 0f, 0f, 0f).apply {
            duration = 800
        })

        toolbar.animate()
            .scaleX(0.1f)
            .scaleY(0.1f)
            .withEndAction {
                toolbar.animate().scaleX(1.0f).scaleY(1.0f).start()
            }
            .start()


        toolbar.setNavigationOnClickListener {
            toolbar.bumpAnimation()
            returnToPreviousScreen()
        }
    }

    protected fun setupRecyclerView(recyclerView: RecyclerView, baseAdapter: RecyclerView.Adapter<*>) {
        recyclerView.apply {
            adapter = baseAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
}
