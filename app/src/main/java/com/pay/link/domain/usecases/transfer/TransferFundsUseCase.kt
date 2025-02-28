package com.pay.link.domain.usecases.transfer

import com.pay.link.domain.models.Transaction
import com.pay.link.domain.repository.AccountRepository
import com.pay.link.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TransferFundsUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(sourceId: Int, destinationId: Int, amount: Double) {
        val accounts = accountRepository.getAccounts()
        val sourceAccount = accounts.find { it.id == sourceId }!!
        val destinationAccount = accounts.find { it.id == destinationId }!!

        if (sourceAccount.balance >= amount) {
            accountRepository.updateBalance(sourceId, newBalance = sourceAccount.balance - amount)
            accountRepository.updateBalance(destinationId, newBalance = destinationAccount.balance + amount)

            transactionRepository.insertTransaction(
                Transaction(
                    id = null, // null because new transaction so room will generate
                    sourceAccountId = sourceId,
                    destinationAccountId = destinationId,
                    amount = amount,
                    timestamp = System.currentTimeMillis(),
                )
            )
        } else {
            throw IllegalArgumentException("Insufficient Balance")
        }
    }
}
