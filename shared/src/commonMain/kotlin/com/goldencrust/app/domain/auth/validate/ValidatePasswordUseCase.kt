package com.goldencrust.app.domain.auth.validate

enum class PasswordError { Empty, TooShort }

class ValidatePasswordUseCase {
    operator fun invoke(password: String): PasswordError? = when {
        password.isEmpty() -> PasswordError.Empty
        password.length < MIN_LENGTH -> PasswordError.TooShort
        else -> null
    }

    private companion object { const val MIN_LENGTH = 6 }
}