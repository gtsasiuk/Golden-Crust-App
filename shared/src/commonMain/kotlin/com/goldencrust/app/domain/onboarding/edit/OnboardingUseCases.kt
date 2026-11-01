package com.goldencrust.app.domain.onboarding.edit

import com.goldencrust.app.domain.onboarding.OnboardingRepository

class SetOnboardingCompletedUseCase(private val repository: OnboardingRepository) {
    operator fun invoke(completed: Boolean = true) = repository.setCompleted(completed)
}