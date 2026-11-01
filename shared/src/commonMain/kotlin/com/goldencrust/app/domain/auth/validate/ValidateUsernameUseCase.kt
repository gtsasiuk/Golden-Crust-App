package com.goldencrust.app.domain.auth.validate

enum class UsernameError { Empty, TooShort }

class ValidateUsernameUseCase {
    operator fun invoke(username: String): UsernameError? = when {
        username.isBlank() -> UsernameError.Empty
        username.trim().length < MIN_LENGTH -> UsernameError.TooShort
        else -> null
    }

    private companion object { const val MIN_LENGTH = 3 }
}