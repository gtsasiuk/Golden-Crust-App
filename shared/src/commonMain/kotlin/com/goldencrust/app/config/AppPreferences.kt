package com.goldencrust.app.config

import com.russhwolf.settings.Settings

class AppPreferences(private val settings: Settings = Settings()) {
    var darkTheme: Boolean?
        get() = settings.getBooleanOrNull(KEY_DARK)
        set(value) {
            if (value == null) settings.remove(KEY_DARK) else settings.putBoolean(KEY_DARK, value)
        }

    var language: String?
        get() = settings.getStringOrNull(KEY_LANG)
        set(value) {
            if (value == null) settings.remove(KEY_LANG) else settings.putString(KEY_LANG, value)
        }

    private companion object {
        const val KEY_DARK = "dark_theme"
        const val KEY_LANG = "language"
    }
}