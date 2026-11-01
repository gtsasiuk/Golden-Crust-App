package com.goldencrust.app.presentation.login

import com.goldencrust.app.domain.auth.validate.PasswordError
import com.goldencrust.app.domain.auth.validate.UsernameError

data class LoginState(
    val username: String = "",
    val password: String = "",
    val usernameError: UsernameError? = null,
    val passwordError: PasswordError? = null,
    val isPasswordVisible: Boolean = false,
)