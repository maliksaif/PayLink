package com.pay.link.domain.models

data class TransactionWithAccountNames(
    val id: Int,
    val sourceAccountId: Int,
    val sourceAccountName: String,
    val destinationAccountId: Int,
    val destinationAccountName: String,
    val amount: Double,
    val timestamp: Long
)
