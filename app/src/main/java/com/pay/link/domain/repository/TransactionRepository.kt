package com.pay.link.domain.repository

import com.pay.link.domain.models.Transaction
import com.pay.link.domain.models.TransactionWithAccountNames
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactions(): Flow<List<Transaction>>
    fun getTransactionsWithAccountNames(): Flow<List<TransactionWithAccountNames>>
    suspend fun insertTransaction(transaction: Transaction)
}
