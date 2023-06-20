package com.example.newsapppp.presentation.screens.authentication.bottomsheet

import android.text.Html
import androidx.core.text.HtmlCompat
import com.example.newsapppp.R
import com.example.newsapppp.common.extensions.makeString
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * This class represents the ViewModel for the AuthBottomSheetFragment.
 * @constructor Creates an instance of the class and injects any necessary dependencies.
 */
@HiltViewModel
class AuthBottomSheetViewModel @Inject constructor() :
    BaseViewModel<SheetState, SheetAction>() {

    override val _state = MutableStateFlow<SheetState>(SheetState.Loading)

    /**
     * Sets up the UI for the AuthBottomSheetFragment by creating an action
     * containing the terms and policy HTML string.
     */
    fun setupUi() {
        val action = Html.fromHtml(
            makeString(R.string.terms_and_policy),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
        emit(SheetState.ShowMessage(action))
    }

    /**
     * Handles the click event for the email address button by creating an action
     * to navigate to the SignInFragment.
     */
    fun onBtnEmailAddressClicked() {
        val action =
            AuthBottomSheetFragmentDirections.actionAuthBottomSheetFragmentToSignInFragment()
        emitAction(SheetAction.Navigate(action))
    }
}
