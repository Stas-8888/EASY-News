package com.example.newsapppp.presentation.screens.authentication.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.newsapppp.databinding.FragmentAuthBottomSheetBinding
import com.example.newsapppp.presentation.extensions.clickAnim
import com.example.newsapppp.presentation.extensions.launchWhenStarted
import com.example.newsapppp.presentation.extensions.navigateDirections
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AuthBottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAuthBottomSheetBinding
    private val viewModel by viewModels<AuthBottomSheetFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        dialog?.window?.attributes?.windowAnimations = R.style.AppBottomSheetDialogTheme
        observeOnShared()
        observeOnState()
        viewModel.setupUi()
        binding.btnEmailAddress.setOnClickListener {
            it.clickAnim()
            viewModel.onBtnEmailAddressClicked()
        }
    }

    private fun observeOnState() = launchWhenStarted {
        viewModel.state.collectLatest { state ->
            when (state) {
                is SheetState.Loading -> {}
                is SheetState.ShowMessage -> binding.tvPolicy.text = state.message
            }
        }
    }

    private fun observeOnShared() = launchWhenStarted {
        viewModel.shared.collectLatest { actions ->
            when (actions) {
                is SheetAction.Navigate -> navigateDirections(actions.navigateTo)
            }
        }
    }
}
