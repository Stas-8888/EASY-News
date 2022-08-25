package com.example.newsapppp.presentation.fragments.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentSettingsBinding
import com.example.newsapppp.databinding.NewNameDialogBinding
import com.example.newsapppp.presentation.fragments.base.BaseFragment
import com.example.newsapppp.presentation.utils.EGYPT
import com.example.newsapppp.presentation.utils.GERMANY
import com.example.newsapppp.presentation.utils.RUSSIA
import com.example.newsapppp.presentation.utils.USA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsFragmentViewModel>() {
    override val viewModel by viewModels<SettingsFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCountryFlag()
        switchDayNight()
        receiveSwitchPosition()
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
    }

    private fun switchDayNight() = switchDayNight.setOnCheckedChangeListener { _, trueOrFalse ->
        if (trueOrFalse) {
            viewModel.nightMode()
        } else {
            viewModel.dayMode()
        }
    }

    private fun receiveSwitchPosition() {
        val switchPosition = viewModel.getSwitchPosition()
        if (switchPosition) {
            switchDayNight.isChecked = false
            toast(getString(R.string.Дневная_тема))
        } else {
            switchDayNight.isChecked = true
            toast(getString(R.string.Ночная_тема))
        }
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.pop_up_menu)
        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.us -> {
                    viewModel.saveCountryFlag(USA)
                    setImageResource(R.drawable.usa)
                    toast(getString(R.string.АмереканскиеНовости))
                }
                R.id.ru -> {
                    viewModel.saveCountryFlag(RUSSIA)
                    setImageResource(R.drawable.russia)
                    toast(getString(R.string.РусскиеНовости))
                }
                R.id.germany -> {
                    viewModel.saveCountryFlag(GERMANY)
                    setImageResource(R.drawable.germany)
                    toast(getString(R.string.НемецкиеНовости))
                }
                R.id.egipt -> {
                    viewModel.saveCountryFlag(EGYPT)
                    setImageResource(R.drawable.egypt)
                    toast(getString(R.string.ЕгипетскиеНовости))
                }
            }
            true
        })
        popup.show()
    }

    private fun setImageResource(res: Int) {
        binding.imCountry.setImageResource(res)
    }

    private fun setCountryFlag() {
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
