package com.goldencrust.app.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            subclass(Route.Home::class, Route.Home.serializer())
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
                    onNavigateToLogin = { backStack[backStack.lastIndex] = Route.Login },
                )
            }
            entry<Route.Login> {
                LoginScreen(
                    isDark = isDark,
                    isUk = isUk,
                    onToggleTheme = onToggleTheme,
                    onToggleLanguage = onToggleLanguage,
                    onNavigateToHome = { backStack[backStack.lastIndex] = Route.Home },
                )
            }
            entry<Route.Home> {
                Surface(Modifier.fillMaxSize()) { Box(contentAlignment = Alignment.Center) { Text("Home") } }
            }
        },
    )
}