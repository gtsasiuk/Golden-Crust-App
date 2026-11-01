package com.goldencrust.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.goldencrust.app.presentation.login.LoginScreen
import com.goldencrust.app.presentation.onboarding.OnboardingScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

val navSavedStateConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Route.Onboarding::class, Route.Onboarding.serializer())
            subclass(Route.Login::class, Route.Login.serializer())
        }
    }
}

@Composable
fun AppNavigation(
    backStack: MutableList<NavKey>,
    isDark: Boolean,
    isUk: Boolean,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
) {
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Route.Onboarding> {
                OnboardingScreen(
                    isDark = isDark,
                    isUk = isUk,
                    onToggleTheme = onToggleTheme,
                    onToggleLanguage = onToggleLanguage,
                    // ЗАМІНА верхнього елемента: "Назад" не поверне на онбординг
                    onNavigateToLogin = { backStack[backStack.lastIndex] = Route.Login },
                )
            }
            entry<Route.Login> { LoginScreen() }
        },
    )
}