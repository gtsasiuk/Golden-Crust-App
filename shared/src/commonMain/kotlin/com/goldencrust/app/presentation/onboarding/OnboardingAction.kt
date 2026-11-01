package com.goldencrust.app.presentation.onboarding

sealed interface OnboardingAction {
    data class PageChanged(val page: Int) : OnboardingAction
    data object Skip : OnboardingAction
    data object Finish : OnboardingAction
}