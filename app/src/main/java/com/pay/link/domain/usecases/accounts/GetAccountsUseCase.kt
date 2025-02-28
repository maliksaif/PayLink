package com.pay.link.domain.usecases.accounts

import com.pay.link.domain.models.Account
import com.pay.link.domain.repository.AccountRepository
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val repository: AccountRepository
) {
    operator fun invoke(): List<Account> = repository.getAccounts()
}
