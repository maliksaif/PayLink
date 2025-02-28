package com.pay.link.domain.usecases.accounts

import com.pay.link.domain.repository.AccountRepository
import javax.inject.Inject

class DeleteAllAccountsUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    suspend operator fun invoke() {
        accountRepository.deleteAccounts()
    }

}