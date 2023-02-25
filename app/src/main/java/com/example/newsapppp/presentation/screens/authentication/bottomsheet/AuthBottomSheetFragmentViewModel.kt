package com.example.newsapppp.presentation.screens.authentication.bottomsheet

import android.text.Html
import androidx.core.text.HtmlCompat
import com.example.newsapppp.R
import com.example.newsapppp.core.ProvideResources
import com.example.newsapppp.presentation.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthBottomSheetFragmentViewModel @Inject constructor(
    private val provideResources: ProvideResources
) : BaseViewModel<SheetState, SheetAction>() {

    override val _state = MutableStateFlow<SheetState>(SheetState.Loading)
    override val _shared = MutableSharedFlow<SheetAction>()

    fun setupUi() {
        emit(
            SheetState.ShowMessage(
                Html.fromHtml(
                    provideResources.string(R.string.terms_and_policy),
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                )
            )
        )
    }

    fun onBtnEmailAddressClicked() {
        emitShared(
            SheetAction.Navigate(
                AuthBottomSheetFragmentDirections.actionAuthBottomSheetFragmentToSignInFragment()
            )
        )
    }
}
