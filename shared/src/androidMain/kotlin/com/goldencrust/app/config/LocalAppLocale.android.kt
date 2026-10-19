package com.goldencrust.app.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

actual object LocalAppLocale {
    private var default: Locale? = null

    actual val current: String
        @Composable get() = Locale.getDefault().toString()

    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> {
        val configuration = LocalConfiguration.current
        val context = LocalContext.current
        if (default == null) default = Locale.getDefault()
        val new = if (value == null) default!! else Locale.forLanguageTag(value)
        Locale.setDefault(new)
        configuration.setLocale(new)
        @Suppress("DEPRECATION")
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        return LocalConfiguration.provides(configuration)
    }
}