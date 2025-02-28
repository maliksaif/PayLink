package com.pay.link.domain.usecases.common

import javax.inject.Inject
import kotlin.random.Random

class GenerateAccountNumberUseCase @Inject constructor() {

    operator fun invoke(): String {
        return (1..12)
            .map { Random.nextInt(0, 10) }
            .joinToString("")
    }

}