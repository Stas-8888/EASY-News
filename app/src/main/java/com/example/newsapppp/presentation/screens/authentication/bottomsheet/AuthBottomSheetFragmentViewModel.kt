package com.example.newsapppp.presentation.screens.authentication.bottomsheet

import android.text.Html
import androidx.core.text.HtmlCompat
import com.example.newsapppp.R
import com.example.newsapppp.presentation.extensions.makeString
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthBottomSheetFragmentViewModel @Inject constructor() :
    BaseViewModel<SheetState, SheetAction>() {

    override val _state = MutableStateFlow<SheetState>(SheetState.Loading)

    fun setupUi() {
        val action = Html.fromHtml(
            makeString(R.string.terms_and_policy),
            HtmlCompat.FROM_HTML_MODE_COMPACT
        )
        emit(SheetState.ShowMessage(action))
    }

    fun onBtnEmailAddressClicked() {
        val action =
            AuthBottomSheetFragmentDirections.actionAuthBottomSheetFragmentToSignInFragment()
        emitAction(SheetAction.Navigate(action))
    }
}
