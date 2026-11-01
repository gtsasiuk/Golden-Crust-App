package com.goldencrust.app.domain.usecase

import com.goldencrust.app.domain.onboarding.OnboardingRepository

class IsOnboardingCompletedUseCase(private val repository: OnboardingRepository) {
    operator fun invoke(): Boolean = repository.isCompleted()
}

class SetOnboardingCompletedUseCase(private val repository: OnboardingRepository) {
    operator fun invoke(completed: Boolean = true) = repository.setCompleted(completed)
}