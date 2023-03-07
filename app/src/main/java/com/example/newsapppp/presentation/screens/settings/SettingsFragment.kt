package com.example.newsapppp.presentation.screens.settings

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.newsapppp.R
import com.example.newsapppp.databinding.FragmentSettingsBinding
import com.example.newsapppp.databinding.NewNameDialogBinding
import com.example.newsapppp.presentation.extensions.*
import com.example.newsapppp.presentation.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment :
    BaseFragment<SettingsState, SettingsAction, FragmentSettingsBinding, SettingsFragmentViewModel>(
        FragmentSettingsBinding::inflate
    ) {
    override val viewModel by viewModels<SettingsFragmentViewModel>()
    private var mImageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setupUi()
        setupViewAnimation()
    }

    override fun onClickListener() = with(binding) {
        btAccount.setOnClickListener {
            viewModel.onAccountClicked()
        }
        toolbar.setNavigationOnClickListener {
            it.bump()
            backPress()
        }
        imCountry.setOnClickListener {
            it.clickAnim()
            showPopup(imCountry)
        }
        tvEdit.setOnClickListener {
            showChangeNameDialog()
        }
        switchDayNight.setOnCheckedChangeListener { _, isNightMode ->
            viewModel.onSwitchDayNightClicked(isNightMode)
        }

        profileImage.setOnClickListener {
            profileImage.clickAnim()
            val popupMenu = PopupMenu(context, profileImage)
            popupMenu.menuInflater.inflate(R.menu.profile_photo_storage, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                viewModel.onProfileImageClicked(item, selectImageFromGallery())
                true
            }
            popupMenu.show()
        }
    }

    private fun selectImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            mImageUri = result.data?.data
            showUserImage()
        }

    private fun showUserImage() {
        if (mImageUri != null)
            binding.profileImage.setImageURI(mImageUri)
    }

    override fun observerShared(actions: SettingsAction) {
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

    private fun setupViewAnimation() = with(binding) {
        tvVersion.showWithAnimate(R.anim.fade_in)
        toolbar.startAnimation(TranslateAnimation(1000f, 0f, 0f, 0f).apply {
            duration = 800
        })

        toolbar.animate()
            .scaleX(0.1f)
            .scaleY(0.1f)
            .withEndAction {
                toolbar.animate().scaleX(1.0f).scaleY(1.0f).start()
            }
            .start()
    }
}
