package com.pay.link.domain.models

data class TransactionWithAccountNames(
    val sourceAccountId: Long,
    val sourceAccountName: String,
    val sourceAccountNumber: String,
    val destinationAccountId: Long,
    val destinationAccountName: String,
    val destinationAccountNumber: String,
    val amount: Double,
    val timestamp: Long
)
