package com.goldencrust.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import co.touchlab.kermit.Logger
import com.goldencrust.app.config.AppPreferences
import com.goldencrust.app.config.LocalAppLocale
import com.goldencrust.app.ui.onboarding.OnboardingScreen
import com.goldencrust.app.ui.theme.GoldenCrustTheme

private val log = Logger.withTag("App")

@Composable
fun App() {
    val prefs = remember { AppPreferences() }
    var onboardingDone by remember { mutableStateOf(prefs.onboardingCompleted) }
    var darkOverride by remember { mutableStateOf(prefs.darkTheme) }
    var language by remember { mutableStateOf(prefs.language) }
    var onboardingPage by remember { mutableIntStateOf(0) }

    val isDark = darkOverride ?: isSystemInDarkTheme()

    CompositionLocalProvider(LocalAppLocale provides language) {
        key(language) {
            val isUk = LocalAppLocale.current.startsWith("uk")

            GoldenCrustTheme(darkTheme = isDark) {
                if (!onboardingDone) {
                    OnboardingScreen(
                        initialPage = onboardingPage,
                        onPageChange = { onboardingPage = it },
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
                        onFinish = {
                            log.i { "Onboarding finished" }
                            prefs.onboardingCompleted = true
                            onboardingDone = true
                        },
                    )
                } else {
                    Surface(Modifier.fillMaxSize()) {
                        Box(contentAlignment = Alignment.Center) { Text("Golden Crust") }
                    }
                }
            }
        }
    }
}