package com.goldencrust.app.presentation.login

sealed interface LoginEvent {
    data object NavigateToHome : LoginEvent
}