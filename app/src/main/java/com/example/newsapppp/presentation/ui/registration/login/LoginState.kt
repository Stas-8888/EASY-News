package com.example.newsapppp.presentation.ui.registration.login

import com.example.newsapppp.core.State

sealed class LoginState: State {
    object Success : LoginState()
    data class CheckState(val data: String): LoginState()
    object Error : LoginState()
}
