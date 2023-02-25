package com.example.newsapppp.presentation.screens.authentication.bottomsheet

import android.text.Spanned

sealed class SheetState {
    object Loading : SheetState()
    data class ShowMessage(val message: Spanned) : SheetState()
}
