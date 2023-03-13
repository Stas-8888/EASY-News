package com.example.newsapppp.presentation.screens.settings

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentSettingsBinding
import com.example.newsapppp.databinding.NewNameDialogBinding
import com.example.newsapppp.presentation.extensions.clickAnimation
import com.example.newsapppp.presentation.extensions.navigateDirections
import com.example.newsapppp.presentation.extensions.showSnackBar
import com.example.newsapppp.presentation.extensions.showSnackBarCansel
import com.example.newsapppp.presentation.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

private const val IMAGE_TYPE = "image/*"
private const val SELECT_PICTURE_TITLE = "Select Picture"

@AndroidEntryPoint
class SettingsFragment :
    BaseFragment<SettingsState, SettingsAction, FragmentSettingsBinding, SettingsFragmentViewModel>(
        FragmentSettingsBinding::inflate
    ) {
    override val viewModel by viewModels<SettingsFragmentViewModel>()
    private var mImageUri: Uri? = null
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            handleActivityResult(result)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setupUi()
        initialToolBar(binding.toolbar)
    }

    override fun onClickListener() = with(binding) {
        btAccount.setOnClickListener {
            viewModel.onAccountClicked()
        }
        imCountry.setOnClickListener {
            it.clickAnimation()
            showPopup(imCountry)
        }
        tvEdit.setOnClickListener {
            showChangeNameDialog()
        }
        switchDayNight.setOnCheckedChangeListener { _, isNightMode ->
            viewModel.onSwitchDayNightClicked(isNightMode)
        }

        profileImage.setOnClickListener {
            profileImage.clickAnimation()
            val popupMenu = PopupMenu(context, profileImage)
            popupMenu.menuInflater.inflate(R.menu.profile_photo_storage, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                viewModel.onProfileImageClicked(item) { launchImagePicker() }
                true
            }
            popupMenu.show()
        }
    }

    private fun launchImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = IMAGE_TYPE
        }
        resultLauncher.launch(Intent.createChooser(intent, SELECT_PICTURE_TITLE))
    }

    private fun handleActivityResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { imageUri ->
                mImageUri = imageUri
                binding.profileImage.setImageURI(imageUri)
            }
        }
    }

    override fun observerAction(actions: SettingsAction) {
        when (actions) {
            is SettingsAction.ShowAccount -> {
                showSnackBarCansel(actions.message, actions.isError, actions.action)
            }
            is SettingsAction.Navigate -> navigateDirections(actions.navigateTo)
            is SettingsAction.ShowMessage -> showSnackBar(actions.message)
        }
    }

    override fun observerState(state: SettingsState) = with(binding) {
        when (state) {
            is SettingsState.ShowUi -> {
                tvEmail.text = state.email
                imCountry.setImageResource(state.flag)
                switchDayNight.isChecked = state.theme
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
                bCreate.text = getString(R.string.update)
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
            window?.attributes?.windowAnimations = R.style.DialogAnimation
            show()
        }
    }
}
