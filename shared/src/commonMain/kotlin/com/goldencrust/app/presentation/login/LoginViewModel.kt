package com.goldencrust.app.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.goldencrust.app.domain.auth.validate.ValidatePasswordUseCase
import com.goldencrust.app.domain.auth.validate.ValidateUsernameUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateUsername: ValidateUsernameUseCase,
    private val validatePassword: ValidatePasswordUseCase,
) : ViewModel() {
    private val log = Logger.withTag("LoginVM")

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _events = Channel<LoginEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.UsernameChanged ->
                _state.update { it.copy(username = action.value, usernameError = null) }
            is LoginAction.PasswordChanged ->
                _state.update { it.copy(password = action.value, passwordError = null) }
            LoginAction.TogglePasswordVisibility ->
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            LoginAction.SignIn -> signIn()
            LoginAction.ForgotPasswordClicked -> log.d { "Forgot password clicked (not implemented)" }
            LoginAction.RegisterClicked -> log.d { "Register clicked (not implemented)" }
        }
    }

    private fun signIn() {
        val current = _state.value
        val usernameError = validateUsername(current.username)
        val passwordError = validatePassword(current.password)
        _state.update { it.copy(usernameError = usernameError, passwordError = passwordError) }

        if (usernameError != null || passwordError != null) {
            log.d { "Validation failed: $usernameError / $passwordError" }
            return
        }
        log.i { "Signed in as ${current.username.trim()}" }
        viewModelScope.launch { _events.send(LoginEvent.NavigateToHome) }
    }
}