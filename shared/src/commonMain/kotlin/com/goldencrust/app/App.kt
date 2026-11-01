package com.goldencrust.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.navigation3.runtime.rememberNavBackStack
import co.touchlab.kermit.Logger
import com.goldencrust.app.config.AppPreferences
import com.goldencrust.app.config.LocalAppLocale
import com.goldencrust.app.di.appModule
import com.goldencrust.app.di.initKoin
import com.goldencrust.app.presentation.main.MainViewModel
import com.goldencrust.app.presentation.navigation.AppNavigation
import com.goldencrust.app.presentation.navigation.navSavedStateConfig
import com.goldencrust.app.presentation.ui.theme.GoldenCrustTheme
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.mp.KoinPlatform

private val log = Logger.withTag("App")

@Composable
fun App() {
    remember { KoinPlatform.getKoinOrNull() ?: initKoin().koin }


    AppContent()
}

@Composable
private fun AppContent() {
    val prefs = remember { AppPreferences() }
    val mainViewModel = koinViewModel<MainViewModel>()

    val backStack = rememberNavBackStack(navSavedStateConfig, mainViewModel.startRoute)

    var darkOverride by remember { mutableStateOf(prefs.darkTheme) }
    var language by remember { mutableStateOf(prefs.language) }
    val isDark = darkOverride ?: isSystemInDarkTheme()

    CompositionLocalProvider(LocalAppLocale provides language) {
        key(language) {
            val isUk = LocalAppLocale.current.startsWith("uk")

            GoldenCrustTheme(darkTheme = isDark) {
                AppNavigation(
                    backStack = backStack,
                    isDark = isDark,
                    isUk = isUk,
                    onToggleTheme = {
                        darkOverride = !isDark
                        prefs.darkTheme = !isDark
                        log.i { "Theme dark=${!isDark}" }
                    },
                    onToggleLanguage = {
                        val new = if (isUk) "en" else "uk"
                        language = new
                        prefs.language = new
                        log.i { "Language=$new" }
                    },
                )
            }
        }
    }
}