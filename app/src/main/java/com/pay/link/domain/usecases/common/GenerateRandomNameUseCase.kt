package com.pay.link.domain.usecases.common

import javax.inject.Inject

class GenerateRandomNameUseCase @Inject constructor() {

    operator fun invoke(): String {
        val firstNames = listOf("John", "Emma", "Liam", "Olivia", "Noah", "Ava", "Sophia", "James", "Isabella", "Lucas")
        val lastNames = listOf("Smith", "Johnson", "Brown", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin")

        return "${firstNames.random()} ${lastNames.random()}"
    }
}