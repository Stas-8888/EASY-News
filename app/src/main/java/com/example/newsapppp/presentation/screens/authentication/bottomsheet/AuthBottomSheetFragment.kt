package com.example.newsapppp.presentation.screens.authentication.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.newsapppp.databinding.FragmentAuthBottomSheetBinding
import com.example.newsapppp.common.extensions.clickAnimation
import com.example.newsapppp.common.extensions.launchWhenCreated
import com.example.newsapppp.common.extensions.navigateDirections
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AuthBottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAuthBottomSheetBinding
    private val viewModel by viewModels<AuthBottomSheetViewModel>()

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
        observeOnShared()
        observeOnState()
        viewModel.setupUi()
        binding.btnEmailAddress.setOnClickListener {
            it.clickAnimation()
            viewModel.onBtnEmailAddressClicked()
        }
    }

    private fun observeOnState() = launchWhenCreated {
        viewModel.state.collectLatest { state ->
            when (state) {
                is SheetState.Loading -> {}
                is SheetState.ShowMessage -> binding.tvPolicy.text = state.message
            }
        }
    }

    private fun observeOnShared() = launchWhenCreated {
        viewModel.action.collectLatest { actions ->
            when (actions) {
                is SheetAction.Navigate -> navigateDirections(actions.navigateTo)
            }
        }
    }
}
