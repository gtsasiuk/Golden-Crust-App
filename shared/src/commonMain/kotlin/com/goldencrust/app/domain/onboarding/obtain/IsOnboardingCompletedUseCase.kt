package com.goldencrust.app.domain.onboarding.obtain

import com.goldencrust.app.domain.onboarding.OnboardingRepository

class IsOnboardingCompletedUseCase(private val repository: OnboardingRepository) {
    operator fun invoke(): Boolean = repository.isCompleted()
}