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
import com.example.newsapppp.presentation.ui.base.BaseFragment
import com.example.newsapppp.presentation.extensions.hideBottomNavigation
import com.example.newsapppp.presentation.extensions.navigateTo
import com.example.newsapppp.presentation.extensions.showSnackbar
import com.example.newsapppp.presentation.extensions.snackBar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*

@AndroidEntryPoint
class SettingsFragment :
    BaseFragment<SettingsState, FragmentSettingsBinding, SettingsFragmentViewModel>(
        FragmentSettingsBinding::inflate
    ) {
    override val viewModel by viewModels<SettingsFragmentViewModel>()
    lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
        firebaseAuth = FirebaseAuth.getInstance()
        tvEmail.text = firebaseAuth.currentUser?.email
        viewModel.setupCountryFlag()
        viewModel.setupTheme()
    }

    override fun onClickListener() = with(binding) {
        account.setOnClickListener {
            viewModel.checkAccount()
        }
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        imCountry.setOnClickListener {
            showPopup(im_country)
        }
        tvEdit.setOnClickListener {
            showChangeNameDialog("")
        }
        switchDayNight.setOnCheckedChangeListener { _, isNightMode ->
            viewModel.onSwitchDayNightClicked(isNightMode)
        }
    }

    override fun setObserverState(state: SettingsState) {
        when (state) {
            is SettingsState.Account -> {
                showSnackbar(requireView(), state.message, state.isError, state.action)
            }
            is SettingsState.Account2 -> navigateTo(state.navigation)
            is SettingsState.GetCurrentEmail -> state.currentEmail
            is SettingsState.IsSwitch -> switchDayNight.isChecked = state.isSwitch
            is SettingsState.SetupCountryFlag -> binding.imCountry.setImageResource(state.flag)
            is SettingsState.SaveCurrentCountry -> {
                binding.imCountry.setImageResource(state.imageResource)
                snackBar(requireView(), state.countryName)
            }
        }
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.pop_up_menu)
        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.us -> viewModel.saveUsaCountry()
                R.id.ru -> viewModel.saveRussiaCountry()
                R.id.germany -> viewModel.saveGermanyCountry()
                R.id.egipt -> viewModel.saveEgyptCountry()
            }
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
            if (name.isNotEmpty()) bCreate.text = getString(R.string.Update)

            bCreate.setOnClickListener {
                val listName = edNewListName.text.toString()
                if (listName.isNotEmpty()) {
                    tvUserName.text = listName
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
