package com.goldencrust.app.data.onboarding

import com.goldencrust.app.domain.onboarding.OnboardingRepository
import com.russhwolf.settings.Settings

class OnboardingRepositoryImpl(private val settings: Settings) : OnboardingRepository {
    override fun isCompleted(): Boolean = settings.getBoolean(KEY, false)
    override fun setCompleted(completed: Boolean) = settings.putBoolean(KEY, completed)

    private companion object {
        const val KEY = "onboarding_completed" // той самий ключ, що був в AppPreferences
    }
}