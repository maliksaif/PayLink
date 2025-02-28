package com.pay.link.domain.usecases.validations

import javax.inject.Inject

class ValidateEmailAddressUseCase @Inject constructor() {

    companion object {
        private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    }

    operator fun invoke(email: String): Boolean {
        if (email.isEmpty()) return false
        return EMAIL_REGEX.matches(email)
    }
}
