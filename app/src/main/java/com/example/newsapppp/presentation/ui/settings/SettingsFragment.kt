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
        setupCountryFlag()
        viewModel.onSwitchDayNightClick()
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
            viewModel.saveDayNightState(isNightMode)
        }
    }

    override fun renderState(state: SettingsState) {
        when (state) {
            is SettingsState.Account -> {
                showSnackbar(requireView(), state.message, state.isError, state.action)
            }
            is SettingsState.Account2 -> navigateTo(state.navigation)
            is SettingsState.GetCurrentEmail -> state.currentEmail
            is SettingsState.IsSwitch -> switchDayNight.isChecked = state.isSwitch
        }
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.pop_up_menu)
        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.us -> {
                    saveCurrentCountry(
                        view,
                        countryFlag = USA,
                        imageResource = R.drawable.usa,
                        countryName = R.string.American_News
                    )
                }
                R.id.ru -> {
                    saveCurrentCountry(
                        view,
                        countryFlag = RUSSIA,
                        imageResource = R.drawable.russia,
                        countryName = R.string.Russia_News
                    )
                }
                R.id.germany -> {
                    saveCurrentCountry(
                        view,
                        countryFlag = GERMANY,
                        imageResource = R.drawable.germany,
                        countryName = R.string.Germany_News
                    )
                }
                R.id.egipt -> {
                    saveCurrentCountry(
                        view,
                        countryFlag = EGYPT,
                        imageResource = R.drawable.egypt,
                        countryName = R.string.Egypt_News
                    )
                }
            }
            true
        }
        popup.show()
    }

    private fun saveCurrentCountry(
        view: View,
        countryFlag: String,
        imageResource: Int,
        countryName: Int
    ) {
        viewModel.saveCountryFlag(countryFlag)
        setImageResource(imageResource)
        snackBar(view, countryName)
    }

    private fun setImageResource(res: Int) {
        binding.imCountry.setImageResource(res)
    }

    private fun setupCountryFlag() {
        when (viewModel.getCountryFlag()) {
            USA -> {
                setImageResource(R.drawable.usa)
            }
            GERMANY -> {
                setImageResource(R.drawable.germany)
            }
            RUSSIA -> {
                setImageResource(R.drawable.russia)
            }
            EGYPT -> {
                setImageResource(R.drawable.egypt)
            }
        }
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

    companion object {
        private const val USA = "us"
        private const val GERMANY = "de"
        private const val RUSSIA = "ru"
        private const val EGYPT = "eg"
    }
}
