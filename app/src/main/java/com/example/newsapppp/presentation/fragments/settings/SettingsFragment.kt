package com.example.newsapppp.presentation.fragments.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentSettingsBinding
import com.example.newsapppp.databinding.NewNameDialogBinding
import com.example.newsapppp.presentation.fragments.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*

private const val USA = "us"
private const val GERMANY = "de"
private const val RUSSIA = "ru"
private const val EGYPT = "eg"

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsFragmentViewModel>() {
    override val viewModel by viewModels<SettingsFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCountryFlag()
        viewModel.getSwitchPosition()
        setupSwitchPosition()
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.imCountry.setOnClickListener {
            showPopup(im_country)
        }
        tvEdit.setOnClickListener {
            showChangeNameDialog("")
        }
        switchDayNight.setOnCheckedChangeListener { _, isNightMode ->
            viewModel.saveChangeNightMode(isNightMode)
        }
    }

    private fun setupSwitchPosition() = lifecycleScope.launchWhenStarted {
        viewModel.state.collect() {
            when (it) {
                is SettingsState.SwitchPosition -> isChecked(false)
                is SettingsState.UnSwitchPosition -> isChecked(true)
            }
        }
    }

    private fun isChecked(value: Boolean) {
        switchDayNight.isChecked = value
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.pop_up_menu)
        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.us -> {
                    setCurrentCountry(
                        countryFlag = USA,
                        imageResource = R.drawable.usa,
                        countryName = getString(R.string.АмереканскиеНовости)
                    )
                }
                R.id.ru -> {
                    setCurrentCountry(
                        countryFlag = RUSSIA,
                        imageResource = R.drawable.russia,
                        countryName = getString(R.string.РусскиеНовости)
                    )
                }
                R.id.germany -> {
                    setCurrentCountry(
                        countryFlag = GERMANY,
                        imageResource = R.drawable.germany,
                        countryName = getString(R.string.НемецкиеНовости)
                    )
                }
                R.id.egipt -> {
                    setCurrentCountry(
                        countryFlag = EGYPT,
                        imageResource = R.drawable.egypt,
                        countryName = getString(R.string.ЕгипетскиеНовости)
                    )
                }
            }
            true
        })
        popup.show()
    }

    private fun setCurrentCountry(countryFlag: String, imageResource: Int, countryName: String) {
        viewModel.saveCountryFlag(countryFlag)
        setImageResource(imageResource)
        toast(countryName)
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
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSettingsBinding.inflate(inflater, container, false)
}
