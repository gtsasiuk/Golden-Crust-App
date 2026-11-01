package com.goldencrust.app.presentation.main

import androidx.lifecycle.ViewModel
import com.goldencrust.app.domain.onboarding.obtain.IsOnboardingCompletedUseCase
import com.goldencrust.app.presentation.navigation.Route

class MainViewModel(
    isOnboardingCompleted: IsOnboardingCompletedUseCase,
) : ViewModel() {
    val startRoute: Route =
        if (isOnboardingCompleted()) Route.Login else Route.Onboarding
}