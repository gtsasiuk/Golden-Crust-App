package com.goldencrust.app.domain.onboarding

interface OnboardingRepository {
    fun isCompleted(): Boolean
    fun setCompleted(completed: Boolean)
}