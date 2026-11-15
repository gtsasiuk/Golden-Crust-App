package com.goldencrust.app.presentation.ui

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass

enum class WindowLayout { Compact, Wide }

@Composable
fun rememberWindowLayout(): WindowLayout {
    val sizeClass = currentWindowAdaptiveInfo().windowSizeClass
    return if (sizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND))
        WindowLayout.Wide
    else
        WindowLayout.Compact
}