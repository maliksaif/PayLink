package com.pay.link.domain.usecases.accounts

import com.pay.link.domain.models.Account
import com.pay.link.domain.models.BankName
import com.pay.link.domain.repository.AccountRepository
import javax.inject.Inject

class GenerateMockAccountDataUseCase @Inject constructor(private val accountRepository: AccountRepository) {

    suspend operator fun invoke(name: String, number: String, balance: Double,bankName:BankName) {
        accountRepository.mockData(
            Account(
                id = -1, // will  be ignore or we can make it nullable
                holder = name,
                number = number,
                balance = balance,
                bankName = bankName.displayName
            )
        )

    }
}