package com.example.newsapppp.presentation.ui.registration.sign

import com.example.newsapppp.core.State

sealed class SignUpState: State {

    object Success : SignUpState()

    object Error : SignUpState()
}
