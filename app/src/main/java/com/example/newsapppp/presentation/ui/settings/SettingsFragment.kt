package com.example.newsapppp.presentation.ui.settings

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentSettingsBinding
import com.example.newsapppp.databinding.NewNameDialogBinding
import com.example.newsapppp.presentation.extensions.navigateTo
import com.example.newsapppp.presentation.extensions.showSnackBarCansel
import com.example.newsapppp.presentation.extensions.showSnackBarInt
import com.example.newsapppp.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment :
    BaseFragment<SettingsState, SettingsAction, FragmentSettingsBinding, SettingsFragmentViewModel>(
        FragmentSettingsBinding::inflate
    ) {
    override val viewModel by viewModels<SettingsFragmentViewModel>()

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
            showChangeNameDialog("")
        }
        switchDayNight.setOnCheckedChangeListener { _, isNightMode ->
            viewModel.onSwitchDayNightClicked(isNightMode)
        }
    }

    override fun observerShared(actions: SettingsAction) {
    }

    override fun observerState(state: SettingsState) = with(binding) {
        when (state) {
            is SettingsState.SetupUi -> {
                tvEmail.text = state.email
                switchDayNight.isChecked = state.theme
                imCountry.setImageResource(state.flag)
            }
            is SettingsState.SaveCurrentCountry -> {
                showSnackBarInt(requireView(), state.countryName)
                imCountry.setImageResource(state.countryFlag)
            }
            is SettingsState.Navigate -> navigateTo(state.navigation)
            is SettingsState.Account -> {
                showSnackBarCansel(requireView(), state.message, state.isError, state.action)
            }
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

    private fun showChangeNameDialog(name: String) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = NewNameDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            edNewListName.setText(name)
            if (name.isNotEmpty()) {
                bCreate.text = getString(R.string.Update)
            }
            bCreate.setOnClickListener {
                val listName = edNewListName.text.toString()
                if (listName.isNotEmpty()) {
//                    tvUserName.text = listName
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
