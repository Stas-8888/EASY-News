package com.example.newsapppp.presentation.ui.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentSettingsBinding
import com.example.newsapppp.databinding.NewNameDialogBinding
import com.example.newsapppp.presentation.extensions.navigateDirections
import com.example.newsapppp.presentation.extensions.showSnackBar
import com.example.newsapppp.presentation.extensions.showSnackBarCansel
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment :
    BaseFragment<SettingsState, SettingsAction, FragmentSettingsBinding, SettingsFragmentViewModel>(
        FragmentSettingsBinding::inflate
    ) {
    override val viewModel by viewModels<SettingsFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setupUi()
    }

    override fun onClickListener() = with(binding) {
        btAccount.setOnClickListener {
            viewModel.onAccountClicked()
        }
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        imCountry.setOnClickListener {
            showPopup(imCountry)
        }
        tvEdit.setOnClickListener {
            showChangeNameDialog()
        }
        switchDayNight.setOnCheckedChangeListener { _, isNightMode ->
            viewModel.onSwitchDayNightClicked(isNightMode)
        }
    }

    override fun observerShared(actions: SettingsAction) {
        when (actions) {
            is SettingsAction.Account -> {
                showSnackBarCansel(actions.message, actions.isError, actions.action)
            }
            is SettingsAction.Navigate -> navigateDirections(actions.navigateTo)
            is SettingsAction.Message -> showSnackBar(actions.message)
        }
    }

    override fun observerState(state: SettingsState) = with(binding) {
        when (state) {
            is SettingsState.SetupUi -> {
                tvEmail.text = state.email
                switchDayNight.isChecked = state.theme
                imCountry.setImageResource(state.flag)
            }
            is SettingsState.SetCurrentCountry -> imCountry.setImageResource(state.countryFlag)
        }
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.pop_up_menu)
        popup.setOnMenuItemClickListener { item: MenuItem ->
            viewModel.setupPopupMenu(item)
            true
        }
        popup.show()
    }

    private fun showChangeNameDialog() {
        var typeText = ""
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = NewNameDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            val name = edNewListName.text
            if (name.isNotEmpty()) {
                bCreate.text = getString(R.string.Update)
            }
            bCreate.setOnClickListener {
                val listName = edNewListName.text.toString()
                if (listName.isNotEmpty()) {
                    typeText = listName
                }
                dialog?.dismiss()
            }
        }
        dialog = builder.create().apply {
            window?.setBackgroundDrawable(null)
            show()
        }
    }
}
