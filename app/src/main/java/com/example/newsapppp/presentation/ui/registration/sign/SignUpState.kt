package com.example.newsapppp.presentation.ui.registration.sign

import com.example.newsapppp.core.State

sealed class SignUpState : State {

    object Success : SignUpState()

    data class CheckState(
        val name: String,
        val email: String,
        val password: String,
        val repeatPassword: String
    ) : SignUpState()

    object Error : SignUpState()
}
