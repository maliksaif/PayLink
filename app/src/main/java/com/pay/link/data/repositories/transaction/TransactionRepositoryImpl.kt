package com.pay.link.data.repositories.transaction

import com.pay.link.data.local.dao.TransactionDao
import com.pay.link.data.local.entities.TransactionEntity
import com.pay.link.domain.models.Transaction
import com.pay.link.domain.models.TransactionWithAccountNames
import com.pay.link.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(private val transactionDao: TransactionDao) :
    TransactionRepository {

    override fun getTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions().map { list ->
            list.map { Transaction(it.id, it.sourceAccountId, it.destinationAccountId, it.amount, it.timestamp) }
        }
    }

    override fun getTransactionsWithAccountNames(): List<TransactionWithAccountNames> {
        return transactionDao.getTransactionsWithAccountNames()
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(
            TransactionEntity(
                sourceAccountId = transaction.sourceAccountId,
                destinationAccountId = transaction.destinationAccountId,
                amount = transaction.amount,
                timestamp = transaction.timestamp
            )
        )
    }
}
