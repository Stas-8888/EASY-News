package com.example.newsapppp.presentation.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.newsapppp.core.State
import com.example.newsapppp.presentation.utils.extensions.launchWhenStarted
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collectLatest

abstract class BaseFragment<S : State, VB : ViewBinding, VM : BaseViewModel<S>>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected abstract val viewModel: VM
//    lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
//        firebaseAuth = FirebaseAuth.getInstance()
        return binding.root
    }

    fun toast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.navigateTo(where: Int) = findNavController().navigate(where)

    fun Fragment.navigateDirections(where: NavDirections) = findNavController().navigate(where)

    fun applicationContext(): Context = requireActivity().applicationContext

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeOnState()
    }

    private fun observeOnState() = launchWhenStarted {
        viewModel.state.collectLatest { state ->
            renderState(state)
        }
    }

    abstract fun renderState(state: S)
}
