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
import com.example.newsapppp.common.Constants.DURATION_800L
import com.example.newsapppp.common.Constants.SCALE_0F
import com.example.newsapppp.common.Constants.SCALE_0_1F
import com.example.newsapppp.common.Constants.SCALE_1000F
import com.example.newsapppp.common.Constants.SCALE_1F
import com.example.newsapppp.presentation.extensions.bumpAnimation
import com.example.newsapppp.presentation.extensions.launchWhenCreated
import com.example.newsapppp.presentation.extensions.returnToPreviousScreen
import kotlinx.coroutines.flow.collectLatest

/**
 * A base fragment class that provides common functionality for fragments.
 * @param State The state of the fragment.
 * @param Action The action that the fragment can perform.
 * @param VB The type of the view binding class for the fragment.
 * @param VM The type of the view model class for the fragment.
 */
abstract class BaseFragment<State, Action, VB : ViewBinding, VM : BaseViewModel<State, Action>>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected abstract val viewModel: VM

    /**
     * Set layout [_binding] to Fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeOnState()
        observeOnAction()
        onClickListener()
    }

    /**
     * Observes the state of the view model and updates the UI accordingly.
     */
    private fun observeOnState() = launchWhenCreated {
        viewModel.state.collectLatest { state ->
            observerState(state)
        }
    }

    /**
     * Observes the actions of the view model and updates the UI accordingly.
     */
    private fun observeOnAction() = launchWhenCreated {
        viewModel.action.collectLatest { actions ->
            observerAction(actions)
        }
    }

    /**
     * Called when a click event occurs in the UI.
     */
    abstract fun onClickListener()

    /**
     * Called when the state of the view model changes.
     *
     * @param state The new state of the view model.
     */
    abstract fun observerState(state: State)

    /**
     * Called when an action is performed by the view model.
     *
     * @param actions The action performed by the view model.
     */
    abstract fun observerAction(actions: Action)

    /**
     * Initializes the toolbar with animation and navigation functionality.
     *
     * @param toolbar The toolbar to be initialized.
     */
    protected fun initialToolBar(toolbar: androidx.appcompat.widget.Toolbar) {
        toolbar.startAnimation(TranslateAnimation(SCALE_1000F, SCALE_0F, SCALE_0F, SCALE_0F).apply {
            duration = DURATION_800L
        })

        toolbar.animate()
            .scaleX(SCALE_0_1F)
            .scaleY(SCALE_0_1F)
            .withEndAction {
                toolbar.animate().scaleX(SCALE_1F).scaleY(SCALE_1F).start()
            }
            .start()

        toolbar.setNavigationOnClickListener {
            toolbar.bumpAnimation()
            returnToPreviousScreen()
        }
    }

    /**
     * Sets up a RecyclerView with the provided [recyclerView] and [baseAdapter].
     * @param recyclerView The RecyclerView to be set up.
     * @param baseAdapter The RecyclerView adapter to be used.
     */
    protected fun setupRecyclerView(
        recyclerView: RecyclerView,
        baseAdapter: RecyclerView.Adapter<*>
    ) {
        recyclerView.apply {
            adapter = baseAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }
}
