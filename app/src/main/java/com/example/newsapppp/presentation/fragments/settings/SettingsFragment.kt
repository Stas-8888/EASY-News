package com.example.newsapppp.presentation.fragments.settings

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentSettingsBinding
import com.example.newsapppp.databinding.NewNameDialogBinding
import com.example.newsapppp.presentation.utils.EGYPT
import com.example.newsapppp.presentation.utils.GERMANY
import com.example.newsapppp.presentation.utils.RUSSIA
import com.example.newsapppp.presentation.utils.USA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPref: SharedPreferences
    private val viewModel by viewModels<SettingsFragmentViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = requireActivity().getSharedPreferences("Table", Context.MODE_PRIVATE)
        setCountryFlag()
        switchDayNight()
        receiveSwitchPosition()
        onClickListener()
    }

    private fun onClickListener(){
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
            saveSwitch(0)
            saveSwitchPosition(false)
            AppCompatDelegate
                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            saveSwitch(1)
            saveSwitchPosition(true)
            AppCompatDelegate
                .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    private fun saveSwitch(switch: Int) {
        val editor = sharedPref.edit()
        editor.putInt("SWITCH_KEY", switch)
        editor.apply()
    }

    private fun saveSwitchPosition(position: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean("SWITCH_POSITION_KEY", position)
        editor.apply()
    }

    private fun receiveSwitchPosition() {
        sharedPref = requireActivity().getSharedPreferences("Table", Context.MODE_PRIVATE)
        val switchPosition = sharedPref.getBoolean("SWITCH_POSITION_KEY", false)
        if (switchPosition) {
            switchDayNight.isChecked = false
            Toast.makeText(requireContext(), getString(R.string.Дневная_тема), Toast.LENGTH_SHORT)
                .show()
        } else {
            switchDayNight.isChecked = true
            Toast.makeText(requireContext(), getString(R.string.Ночная_тема), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun saveCountry(res: String) {
        val editor = sharedPref.edit()
        editor.putString("country", res)
        editor.apply()
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.inflate(R.menu.pop_up_menu)
        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.us -> {
                    saveCountry(USA)
                    binding.imCountry.setImageResource(R.drawable.usa)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.АмереканскиеНовости),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.ru -> {
                    saveCountry(RUSSIA)
                    binding.imCountry.setImageResource(R.drawable.russia)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.РусскиеНовости),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.germany -> {
                    saveCountry(GERMANY)
                    binding.imCountry.setImageResource(R.drawable.germany)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.НемецкиеНовости),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.egipt -> {
                    saveCountry(EGYPT)
                    binding.imCountry.setImageResource(R.drawable.egypt)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.ЕгипетскиеНовости),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            true
        })
        popup.show()
    }

    private fun setCountryFlag() {
        val countryCode =
            requireActivity().getSharedPreferences("Table", Context.MODE_PRIVATE)
                .getString("country", "")
        when (countryCode) {
            USA -> {
                binding.imCountry.setImageResource(R.drawable.usa)
            }
            GERMANY -> {
                binding.imCountry.setImageResource(R.drawable.germany)
            }

            RUSSIA -> {
                binding.imCountry.setImageResource(R.drawable.russia)
            }

            EGYPT -> {
                binding.imCountry.setImageResource(R.drawable.egypt)
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
}
