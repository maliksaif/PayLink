package com.pay.link.domain.usecases.accounts

import com.pay.link.domain.models.Account
import com.pay.link.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(): Flow<List<Account>> = repository.getAccounts()
}
