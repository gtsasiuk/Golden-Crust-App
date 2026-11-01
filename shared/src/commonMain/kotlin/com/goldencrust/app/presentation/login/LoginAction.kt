package com.goldencrust.app.presentation.login

sealed interface LoginAction {
    data class UsernameChanged(val value: String) : LoginAction
    data class PasswordChanged(val value: String) : LoginAction
    data object TogglePasswordVisibility : LoginAction
    data object SignIn : LoginAction
    data object ForgotPasswordClicked : LoginAction
    data object RegisterClicked : LoginAction
}