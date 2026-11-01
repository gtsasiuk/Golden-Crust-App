package com.goldencrust.app.presentation.onboarding

sealed interface OnboardingEvent {
    data object NavigateToLogin : OnboardingEvent
}