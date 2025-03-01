package com.pay.link.domain.usecases.transfer

import com.pay.link.domain.models.Transaction
import com.pay.link.domain.models.TransactionWithAccountNames
import com.pay.link.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTransactionsWithNamesUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): List<TransactionWithAccountNames> = repository.getTransactionsWithAccountNames()
}
