package com.goldencrust.app.di

import com.goldencrust.app.data.onboarding.OnboardingRepositoryImpl
import com.goldencrust.app.domain.auth.validate.ValidatePasswordUseCase
import com.goldencrust.app.domain.auth.validate.ValidateUsernameUseCase
import com.goldencrust.app.domain.onboarding.OnboardingRepository
import com.goldencrust.app.domain.onboarding.edit.SetOnboardingCompletedUseCase
import com.goldencrust.app.domain.onboarding.obtain.IsOnboardingCompletedUseCase
import com.goldencrust.app.presentation.login.LoginViewModel
import com.goldencrust.app.presentation.main.MainViewModel
import com.goldencrust.app.presentation.onboarding.OnboardingViewModel
import com.russhwolf.settings.Settings
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Settings> { Settings() }
    single<OnboardingRepository> { OnboardingRepositoryImpl(get()) }

    factory { IsOnboardingCompletedUseCase(get()) }
    factory { SetOnboardingCompletedUseCase(get()) }
    factory { ValidateUsernameUseCase() }
    factory { ValidatePasswordUseCase() }

    viewModel { OnboardingViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
}