package com.pay.link.domain.models

data class Transaction(
    val id: Int? = null,
    val sourceAccountId: Int,
    val destinationAccountId: Int,
    val amount: Double,
    val timestamp: Long
)
